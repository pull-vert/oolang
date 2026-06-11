/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.compiler;

import oolang.ast.Import;
import oolang.ast.PackageHeader;
import oolang.ast.Type;
import oolang.ast.element.ClassBody;
import oolang.ast.element.ElementModifier;
import oolang.ast.element.RealElement;
import oolang.ast.statement.CodeBlock;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.System.Logger.Level.DEBUG;
import static oolang.ast.element.RealElement.ElementType.PARAMETER;

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
            final var package_= packageHeader.raw().replace('.', '/');
            final var packagePath = rootPath.resolve(package_);
            Files.createDirectories(packagePath);

            assert element.identifier != null;
            final var className = element.identifier.identifier;
            final var classPath = packagePath.resolve(className + ".class");
            classFile.buildTo(classPath, ClassDesc.ofInternalName(package_ + "/" + className), classBuilder -> {
                switch (element.elementType) {
                    case CLASS -> visitClass(classBuilder, element);
                    case INTERFACE -> throw new UnsupportedOperationException();
                    default -> throw new IllegalStateException("Unknown element type: " + element.elementType);
                }
            });
            if (LOGGER.isLoggable(DEBUG)) {
                LOGGER.log(DEBUG, "Compiled {0} {1}", element.elementType, classPath);
            }
            return classPath;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void visitClass(final @NonNull ClassBuilder classBuilder, final @NonNull RealElement classElement) {
        assert classBuilder != null;
        assert classElement != null;

        classBuilder.withFlags(computeFlags(classElement.modifiers));

        for (final var child : classElement.children) {
            if (child instanceof ClassBody classBody) {
                visitClassBody(classBuilder, classBody.children);
            }
        }
    }

    private static void visitClassBody(final @NonNull ClassBuilder classBuilder,
                                       final @NonNull List<@NonNull RealElement> children) {
        assert children != null;

        for (final var child : children) {
            switch (child.elementType) {
                case CLASS -> visitClass(classBuilder, child);
                case INTERFACE,
                     CONSTRUCTOR,
                     VAR,
                     VAL -> throw new UnsupportedOperationException();
                case FUN -> visitFun(classBuilder, child);
                default -> throw new IllegalStateException("Unknown element type: " + child.elementType);
            }
        }
    }

    private static void visitFun(final @NonNull ClassBuilder classBuilder, final @NonNull RealElement funElement) {
        assert classBuilder != null;
        assert funElement != null;

        final var paramDescs = new ArrayList<ClassDesc>();
        final CodeBlock codeBlock;
        for (final var child : funElement.children) {
            if (child instanceof RealElement element &&
                    element.elementType == PARAMETER) {
                paramDescs.add(computeType(element.type));
            }
            if (child instanceof CodeBlock blk) {
                codeBlock = blk;
                break; // this is the last child
            }
        }

        assert funElement.identifier != null;
        classBuilder.withMethodBody(
                funElement.identifier.identifier,
                MethodTypeDesc.of(/* returnDesc */ computeType(funElement.type), paramDescs),
                computeFlags(funElement.modifiers),
                codeBuilder ->
//                    visitFunBody()
                        codeBuilder.getstatic(ClassDesc.ofInternalName("java/lang/System"), "out", ClassDesc.ofInternalName("java/io/PrintStream"))
                                .ldc("Hello World")
                                .invokevirtual(ClassDesc.ofInternalName("java/io/PrintStream"), "println", MethodTypeDesc.ofDescriptor("(Ljava/lang/Object;)V"))
                                .return_());
    }

    private static ClassDesc computeType(final @Nullable Type type) {
        if (type == null) {
            return ConstantDescs.CD_void;
        }
        // specific case for Array
        if (type.identifiers.size() == 1) {
            final var identifier = type.identifiers.getFirst();

            if (identifier.identifier.equals("Array")) {
                return computeType(identifier.parameters.getFirst()).arrayType();
            }
        }
        return ClassDesc.ofInternalName(type.identifiers.stream()
                .map(id -> id.identifier)
                .collect(Collectors.joining("/")));
    }

    private static int computeFlags(final @NonNull List<@NonNull ElementModifier> modifiers) {
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

        return flags(visibility, inheritance, isStatic);
    }

    private static int flags(final @NonNull String visibility,
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

        var flags = visibilityFlag.mask() | inheritanceFlag.mask();
        if (isStatic) {
            flags |= AccessFlag.STATIC.mask();
        }
        return flags;
    }
}
