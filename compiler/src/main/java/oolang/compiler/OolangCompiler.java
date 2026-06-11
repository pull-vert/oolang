/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.compiler;

import oolang.ast.AstType;
import oolang.ast.Import;
import oolang.ast.PackageHeader;
import oolang.ast.element.ClassBody;
import oolang.ast.element.ElementModifier;
import oolang.ast.element.RealElement;
import oolang.ast.expression.ConstantExpression;
import oolang.ast.expression.Expression;
import oolang.ast.expression.LoadExpression;
import oolang.ast.expression.RealExpression;
import oolang.ast.statement.CodeBlock;
import oolang.ast.statement.RealStatement;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.*;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.Logger.Level.DEBUG;
import static oolang.ast.element.RealElement.ElementType.PARAMETER;
import static oolang.ast.expression.JvmInvocationUtils.GET_STATIC;
import static oolang.ast.expression.JvmInvocationUtils.INVOKE_VIRTUAL;
import static oolang.ast.expression.RealExpression.ExpressionType.PROP_ACCESS;
import static oolang.compiler.Descriptors.*;
import static oolang.compiler.StringCompilationUtils.stringConcatenation;

public final class OolangCompiler {
    // un-instantiable
    private OolangCompiler() {
    }

    private static final System.Logger LOGGER = System.getLogger("oolang.compiler.OolangCompiler");

    public static @NonNull Path compile(final @NonNull PackageHeader packageHeader,
                                        final @NonNull List<@NonNull Import> imports,
                                        final @NonNull RealElement element,
                                        final @NonNull Path rootPath) {
        Objects.requireNonNull(packageHeader);
        Objects.requireNonNull(imports);
        Objects.requireNonNull(element);
        Objects.requireNonNull(rootPath);

        final var classFile = ClassFile.of();
        try {
            final var package_ = packageHeader.raw().replace('.', '/');
            final var packagePath = rootPath.resolve(package_);
            Files.createDirectories(packagePath);

            assert element.identifier != null;
            final var className = element.identifier.identifier;
            final var classPath = packagePath.resolve(className + ".class");
            classFile.buildTo(classPath, ClassDesc.ofInternalName(package_ + "/" + className), classBuilder -> {
                switch (element.elementType) {
                    case CLASS -> visitClass(element, classBuilder);
                    case INTERFACE -> throw new UnsupportedOperationException("interface");
                }
            });
            if (LOGGER.isLoggable(DEBUG)) {
                LOGGER.log(DEBUG, "Compiled {0} {1} to {2}",
                        element.elementType.name().toLowerCase(), className, classPath);
            }
            return classPath;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void visitClass(final @NonNull RealElement classElement, final @NonNull ClassBuilder classBuilder) {
        assert classElement != null;
        assert classBuilder != null;

        classBuilder.withFlags(computeModifiers(classElement.modifiers));

        for (final var child : classElement.children) {
            if (child instanceof ClassBody classBody) {
                visitClassBody(classBody, classBuilder);
            }
        }
    }

    private static void visitClassBody(final @NonNull ClassBody classBody, final @NonNull ClassBuilder classBuilder) {
        assert classBody != null;
        assert classBuilder != null;

        for (final var child : classBody.children) {
            switch (child.elementType) {
                case CLASS -> visitClass(child, classBuilder);
                case INTERFACE,
                     CONSTRUCTOR,
                     VAR,
                     VAL -> throw new UnsupportedOperationException();
                case FUN -> visitFun(child, classBuilder);
                default -> throw new IllegalStateException("Unknown element type: " + child.elementType);
            }
        }
    }

    private static void visitFun(final @NonNull RealElement funElement, final @NonNull ClassBuilder classBuilder) {
        assert funElement != null;
        assert classBuilder != null;

        final var paramDescs = new ArrayList<ClassDesc>();
        for (final var child : funElement.children) {
            if (child instanceof RealElement element && element.elementType == PARAMETER) {
                paramDescs.add(computeType(element.type));
            }
        }
        assert funElement.identifier != null;
        final var modifiers = computeModifiers(funElement.modifiers);
        classBuilder.withMethodBody(
                funElement.identifier.identifier,
                MethodTypeDesc.of(/* returnDesc */ computeType(funElement.type), paramDescs),
                computeModifiers(funElement.modifiers),
                codeBuilder -> {
                    if (!Modifier.isAbstract(modifiers)) {
                        // code block is the last child of a non-abstract function
                        visitCodeBlock((CodeBlock) funElement.children.getLast(), codeBuilder);
                    }
                });
    }

    private static void visitCodeBlock(final @NonNull CodeBlock codeBlock, final @NonNull CodeBuilder codeBuilder) {
        assert codeBlock != null;
        assert codeBuilder != null;

        for (final var statement : codeBlock.children) {
            switch (statement) {
                case RealStatement realStatement -> visitStatement(realStatement, codeBuilder);
                default -> throw new UnsupportedOperationException("Unsupported statement: " + statement);
            }
        }
        // add implicit return unless a previous explicit one was declared (todo).
        codeBuilder.return_();
    }

    private static void visitStatement(final @NonNull RealStatement statement, @NonNull CodeBuilder codeBuilder) {
        assert statement != null;
        assert codeBuilder != null;

        for (final var child : statement.children) {
            switch (child) {
                case Expression expression -> visitExpression(expression, codeBuilder);
                default -> throw new UnsupportedOperationException("Unsupported statement child: " + child);
            }
        }
    }

    private static void visitExpression(final @NonNull Expression expression, final @NonNull CodeBuilder codeBuilder) {
        assert expression != null;
        assert codeBuilder != null;

        switch (expression) {
            case ConstantExpression constantExpression -> visitConstantExpression(constantExpression, codeBuilder);
            case LoadExpression loadExpression -> visitLoadExpression(loadExpression, codeBuilder);
            case RealExpression realExpression -> visitRealExpression(realExpression, codeBuilder);
        }
    }

    private static void visitConstantExpression(final @NonNull ConstantExpression constantExpression,
                                                final @NonNull CodeBuilder codeBuilder) {
        assert constantExpression != null;
        assert codeBuilder != null;

        codeBuilder.loadConstant(constantExpression.value);
    }

    private static void visitLoadExpression(final @NonNull LoadExpression loadExpression,
                                            final @NonNull CodeBuilder codeBuilder) {
        assert loadExpression != null;
        assert codeBuilder != null;

        switch (loadExpression.descriptorString()) {
            case INTEGER -> codeBuilder.iload(loadExpression.slot());
            case LONG -> codeBuilder.lload(loadExpression.slot());
            case FLOAT -> codeBuilder.fload(loadExpression.slot());
            case DOUBLE -> codeBuilder.dload(loadExpression.slot());
            default -> codeBuilder.aload(loadExpression.slot());
        }
    }

    private static void visitRealExpression(final @NonNull RealExpression realExpression,
                                            final @NonNull CodeBuilder codeBuilder) {
        assert realExpression != null;
        assert codeBuilder != null;

        switch (realExpression.type) {
            case ADD -> visitAdditiveExpression(realExpression, codeBuilder);
            case INDEXING -> visitIndexingExpression(realExpression, codeBuilder);
            case FUN_CALL -> visitFunCallExpression(realExpression, codeBuilder);
            default -> throw new UnsupportedOperationException("Unsupported expression type: " + realExpression.type);
        }
    }

    private static void visitAdditiveExpression(final @NonNull RealExpression additiveExpression,
                                                final @NonNull CodeBuilder codeBuilder) {
        assert additiveExpression != null;
        assert codeBuilder != null;

        final var descriptorString = additiveExpression.descriptorString;
        assert descriptorString != null;

        switch (descriptorString) {
            case STRING -> stringConcatenation(additiveExpression.children, codeBuilder);
            default -> throw new UnsupportedOperationException("Unsupported additive expression for type : " +
                    descriptorString);
        }
    }

    private static void visitIndexingExpression(final @NonNull RealExpression indexingExpression,
                                                final @NonNull CodeBuilder codeBuilder) {
        assert indexingExpression != null;
        assert codeBuilder != null;

        // loop on fun call children: preliminary expressions and index
        for (final var child : indexingExpression.children) {
            switch (child) {
                case LoadExpression loadExpression -> visitLoadExpression(loadExpression, codeBuilder);
                case RealExpression realExpression -> {
                    assert realExpression.type == PROP_ACCESS;
                    visitPropAccess(realExpression, codeBuilder);
                }
                case ConstantExpression constantExpression -> visitConstantExpression(constantExpression, codeBuilder);
                default -> throw new UnsupportedOperationException("Unsupported expression type: " + child);
            }
        }

        // then load the array
        codeBuilder.aaload();
    }

    private static void visitPropAccess(final @NonNull RealExpression propAccessExpression,
                                        final @NonNull CodeBuilder codeBuilder) {
        assert propAccessExpression != null;
        assert codeBuilder != null;

        assert propAccessExpression.ownerDescriptorString != null;
        assert propAccessExpression.descriptorString != null;
        assert propAccessExpression.invocation != null;
        assert propAccessExpression.identifiers.size() == 1;

        final var owner = ClassDesc.ofDescriptor(propAccessExpression.ownerDescriptorString);
        final var name = propAccessExpression.identifiers.getFirst().identifier; // a property has a simple identifier
        final var type = ClassDesc.ofDescriptor(propAccessExpression.descriptorString);

        switch (propAccessExpression.invocation) {
            case GET_STATIC -> codeBuilder.getstatic(owner, name, type);
            default -> throw new UnsupportedOperationException(propAccessExpression.invocation +
                    " invocation is not supported yet");
        }
    }

    static void visitFunCallExpression(final @NonNull RealExpression funCallExpression,
                                       final @NonNull CodeBuilder codeBuilder) {
        assert funCallExpression != null;
        assert codeBuilder != null;

        // loop on fun call children: preliminary expressions and parameters, if any
        for (final var childRaw : funCallExpression.children) {
            if (!(childRaw instanceof RealExpression child)) {
                throw new IllegalStateException("Fun call children should be RealExpression, was: " +
                        childRaw.getClass());
            }
            switch (child.type) {
                case PROP_ACCESS -> visitPropAccess(child, codeBuilder);
                case FUN_CALL_PARAMETER -> {
                    assert child.children.size() == 1;
                    visitExpression(child.children.getFirst(), codeBuilder);
                }
                default -> throw new UnsupportedOperationException("Unsupported expression type: " + child.type);
            }
        }

        if (funCallExpression instanceof InvokeDynamicExpression invokeDynamicExpression) {
            invokeDynamic(invokeDynamicExpression, codeBuilder);
        } else {
            invokeNonDynamic(funCallExpression, codeBuilder);
        }
    }

    private static void invokeNonDynamic(final @NonNull RealExpression invokeExpression,
                                         final @NonNull CodeBuilder codeBuilder) {
        assert codeBuilder != null;
        assert invokeExpression != null;
        assert invokeExpression.ownerDescriptorString != null;
        assert invokeExpression.identifiers.size() == 1;
        assert invokeExpression.typeDescriptorString != null;
        assert invokeExpression.invocation != null;

        final var owner = ClassDesc.ofDescriptor(invokeExpression.ownerDescriptorString);
        final var name = invokeExpression.identifiers.getFirst().identifier; // a function has a simple identifier
        final var type = MethodTypeDesc.ofDescriptor(invokeExpression.typeDescriptorString);

        switch (invokeExpression.invocation) {
            case INVOKE_VIRTUAL -> codeBuilder.invokevirtual(owner, name, type);
            default -> throw new UnsupportedOperationException(invokeExpression.invocation +
                    " invocation is not supported yet");
        }
    }

    private static void invokeDynamic(final @NonNull InvokeDynamicExpression invokeDynamicExpression,
                                      final @NonNull CodeBuilder codeBuilder) {
        assert codeBuilder != null;
        assert invokeDynamicExpression != null;
        assert invokeDynamicExpression.identifiers.size() == 1;
        assert invokeDynamicExpression.typeDescriptorString != null;

        // a function has a simple identifier
        final var invocationName = invokeDynamicExpression.identifiers.getFirst().identifier;
        final var invocationType = MethodTypeDesc.ofDescriptor(invokeDynamicExpression.typeDescriptorString);

        // Create the invokedynamic call site descriptor
        final var callSite = DynamicCallSiteDesc.of(
                invokeDynamicExpression.bootstrapMethod,
                invocationName,
                invocationType,
                invokeDynamicExpression.bootstrapArgs.toArray(new ConstantDesc[0])
        );

        codeBuilder.invokedynamic(callSite);
    }

    private static ClassDesc computeType(final @Nullable AstType type) {
        if (type == null) {
            return ConstantDescs.CD_void;
        }

        return ClassDesc.ofDescriptor(type.descriptorString);
    }

    private static int computeModifiers(final @NonNull List<@NonNull ElementModifier> modifiers) {
        assert modifiers != null;

        var visibility = "public";
        var inheritance = "final";
        var isStatic = false;
        for (final var modifier : modifiers) {
            switch (modifier.type()) {
                case VISIBILITY -> visibility = modifier.modifier();
                case INHERITANCE -> inheritance = modifier.modifier();
                case MEMBER -> {
                    if (modifier.modifier().equals("static")) {
                        isStatic = true;
                    }
                }
                default -> throw new IllegalStateException("Unknown modifier type: " + modifier.type());
            }
        }

        return modifiers(visibility, inheritance, isStatic);
    }

    private static int modifiers(final @NonNull String visibility,
                                 final @NonNull String inheritance,
                                 final boolean isStatic) {
        assert visibility != null;
        assert inheritance != null;

        final var visibilityFlag = switch (visibility) {
            case "public" -> AccessFlag.PUBLIC;
            case "private" -> AccessFlag.PRIVATE;
            case "protected" -> AccessFlag.PROTECTED;
            default -> throw new IllegalStateException("Unknown visibility: " + visibility);
        };

        final var inheritanceFlag = switch (inheritance) {
            case "final" -> AccessFlag.FINAL;
            case "abstract" -> AccessFlag.ABSTRACT;
            case "open" -> AccessFlag.SUPER;
            default -> throw new IllegalStateException("Unknown inheritance: " + inheritance);
        };

        var modifiers = visibilityFlag.mask() | inheritanceFlag.mask();
        if (isStatic) {
            modifiers |= AccessFlag.STATIC.mask();
        }
        return modifiers;
    }
}
