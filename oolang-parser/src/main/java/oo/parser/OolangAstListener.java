package oo.parser;

import oo.ast.FileAst;
import oo.ast.element.ElementAnnotation;
import oo.ast.element.ElementDeclaration;
import oo.ast.element.ElementIdentifier;
import oo.ast.element.ElementModifier;
import oo.parser.generated.OolangParser;
import oo.parser.generated.OolangParserBaseListener;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static oo.ast.element.ElementDeclaration.ElementType.*;

public final class OolangAstListener extends OolangParserBaseListener {
    private final @NonNull Deque<ElementDeclaration.@NonNull Builder> stack = new LinkedList<>();

    private final FileAst.@NonNull Builder fileAstBuilder = new FileAst.Builder();
    private FileAst fileAst = null;

    public @NonNull FileAst fileAst() {
        if (fileAst == null) {
            throw new IllegalStateException("fileAst is not built yet");
        }
        return fileAst;
    }

    @Override
    public void enterClassDeclaration(final OolangParser.@NonNull ClassDeclarationContext ctx) {
        assert ctx != null;
        stack.push(new ElementDeclaration.Builder().elementType(CLASS));
    }

    @Override
    public void exitClassDeclaration(final OolangParser.@NonNull ClassDeclarationContext ctx) {
        assert ctx != null;

        final var classBuilder = stack.pop();
        if (ctx.simpleIdentifier() != null) {
            final var classId = new ElementIdentifier.Builder(ctx.simpleIdentifier().getText()).build();
            classBuilder.identifier(classId);
        }
        addModifiersAndAnnotations(ctx.modifiers(), classBuilder);

        final var element = classBuilder.build();

        final var parent = stack.peek();
        if (parent != null) {
            parent.addChild(element);
        } else {
            fileAstBuilder.addRootElement(element);
        }
    }

    @Override
    public void enterPrimaryConstructor(final OolangParser.@NonNull PrimaryConstructorContext ctx) {
        assert ctx != null;
        stack.push(new ElementDeclaration.Builder().elementType(CONSTRUCTOR));
    }

    @Override
    public void exitPrimaryConstructor(final OolangParser.@NonNull PrimaryConstructorContext ctx) {
        assert ctx != null;

        // add constructor to the current class declaration
        final var constructorBuilder = stack.pop();
        final var classBuilder = stack.element();
        classBuilder.addChild(constructorBuilder.build());
    }

    @Override
    public void exitClassParameter(final OolangParser.@NonNull ClassParameterContext ctx) {
        assert ctx != null;

        var classParamBuilder = new ElementDeclaration.Builder();
        // add parameter to the constructor
        if (ctx.VAL() != null) {
            classParamBuilder.elementType(VAL);
        } else if (ctx.VAR() != null) {
            classParamBuilder.elementType(VAR);
        } else {
            classParamBuilder.elementType(PARAMETER);
        }
        final var paramId = new ElementIdentifier.Builder(ctx.simpleIdentifier().getText()).build();
        classParamBuilder.identifier(paramId);
        final var paramTypeId = new ElementIdentifier.Builder(ctx.type().getText()).build();
        classParamBuilder.type(List.of(paramTypeId));
        addModifiersAndAnnotations(ctx.modifiers(), classParamBuilder);

        final var constructorBuilder = stack.element();
        constructorBuilder.addChild(classParamBuilder.build());
    }

    @Override
    public void enterClassBody(final OolangParser.@NonNull ClassBodyContext ctx) {
        assert ctx != null;
        stack.push(new ElementDeclaration.Builder().elementType(CLASS_BODY));
    }

    @Override
    public void exitClassBody(final OolangParser.@NonNull ClassBodyContext ctx) {
        assert ctx != null;

        // add class body to the current class declaration
        final var classBodyBuilder = stack.pop(); // body builder, funny (˶ˆᗜˆ˵)
        final var classBuilder = stack.element();
        classBuilder.addChild(classBodyBuilder.build());
    }

    @Override
    public void enterFunctionDeclaration(final OolangParser.@NonNull FunctionDeclarationContext ctx) {
        assert ctx != null;
        stack.push(new ElementDeclaration.Builder().elementType(FUN));
    }

    @Override
    public void exitFunctionDeclaration(final OolangParser.@NonNull FunctionDeclarationContext ctx) {
        assert ctx != null;

        final var functionBuilder = stack.pop();
        final var functionId = new ElementIdentifier.Builder(ctx.simpleIdentifier().getText()).build();
        functionBuilder.identifier(functionId);
        if (ctx.type() != null) {
            final var functionTypeId = new ElementIdentifier.Builder(ctx.type().getText()).build();
            functionBuilder.type(List.of(functionTypeId));
        }
        addModifiersAndAnnotations(ctx.modifiers(), functionBuilder);

        // add function to the parent
        final var parent = stack.element();
        parent.addChild(functionBuilder.build());
    }

    @Override
    public void exitFunctionValueParameter(final OolangParser.@NonNull FunctionValueParameterContext ctx) {
        assert ctx != null;

        var functionParamBuilder = new ElementDeclaration.Builder().elementType(PARAMETER);
        // add parameter to the function
        final var parameterCtx = ctx.parameter();
        final var paramId = new ElementIdentifier.Builder(parameterCtx.simpleIdentifier().getText()).build();
        functionParamBuilder.identifier(paramId);
        final var paramTypeId = new ElementIdentifier.Builder(parameterCtx.type().getText()).build();
        functionParamBuilder.type(List.of(paramTypeId));

        if (ctx.parameterModifiers() != null) {
            if (ctx.parameterModifiers().VARARG() != null) {
                functionParamBuilder.addModifier(new ElementModifier("vararg", ElementModifier.ModifierType.PARAMETER));
            }
            addAnnotations(ctx.parameterModifiers().annotation(), functionParamBuilder);
        }

        final var functionBuilder = stack.element();
        functionBuilder.addChild(functionParamBuilder.build());
    }

    @Override
    public void exitOolangFile(final OolangParser.@NonNull OolangFileContext ctx) {
        assert ctx != null;

        this.fileAst = fileAstBuilder.build();
    }

    private static void addModifiersAndAnnotations(final OolangParser.@Nullable ModifiersContext ctx,
                                                   final ElementDeclaration.@NonNull Builder elementBuilder) {
        assert elementBuilder != null;
        if (ctx == null) {
            return;
        }

        for (final var modifierCtx : ctx.modifier()) {
            addModifier(modifierCtx, elementBuilder);
        }
        addAnnotations(ctx.annotation(), elementBuilder);
    }

    private static void addModifier(final OolangParser.@NonNull ModifierContext ctx,
                                    final ElementDeclaration.@NonNull Builder elementBuilder) {
        assert ctx != null;
        assert elementBuilder != null;

        final ElementModifier.ModifierType group;
        if (ctx.visibilityModifier() != null) {
            group = ElementModifier.ModifierType.VISIBILITY;
        } else if (ctx.classModifier() != null) {
            group = ElementModifier.ModifierType.CLASS;
        } else if (ctx.VARARG() != null) {
            group = ElementModifier.ModifierType.PARAMETER;
        } else if (ctx.inheritanceModifier() != null) {
            group = ElementModifier.ModifierType.INHERITANCE;
        } else if (ctx.memberModifier() != null) {
            group = ElementModifier.ModifierType.MEMBER;
        } else {
            throw new IllegalStateException("Unknown modifier: " + ctx.getText());
        }
        elementBuilder.addModifier(new ElementModifier(ctx.getText(), group));
    }

    private static void addAnnotations(final @NonNull List<OolangParser.@NonNull AnnotationContext> annotationContexts,
                                       ElementDeclaration.@NonNull Builder elementBuilder) {
        assert annotationContexts != null;
        assert elementBuilder != null;

        for (final var annotationCtx : annotationContexts) {
            if (annotationCtx.singleAnnotation() != null) {
                addAnnotation(annotationCtx.singleAnnotation().unescapedAnnotation(),
                        annotationCtx.singleAnnotation().annotationUseSiteTarget(), elementBuilder);
            } else if (annotationCtx.multiAnnotation() != null) {
                for (final var unescapedAnnotation : annotationCtx.multiAnnotation().unescapedAnnotation()) {
                    addAnnotation(unescapedAnnotation, annotationCtx.multiAnnotation().annotationUseSiteTarget(), elementBuilder);
                }
            }
        }
    }

    private static void addAnnotation(
            final OolangParser.@NonNull UnescapedAnnotationContext unescapedAnnotationContext,
            final OolangParser.@Nullable AnnotationUseSiteTargetContext annotationUseSiteTargetContext,
            final ElementDeclaration.@NonNull Builder elementBuilder) {
        assert unescapedAnnotationContext != null;
        assert elementBuilder != null;

        final var annotationIdentifier = new ElementIdentifier.Builder(unescapedAnnotationContext.getText()).build();
        final var annotationBuilder = new ElementAnnotation.Builder(List.of(annotationIdentifier));
        if (annotationUseSiteTargetContext != null) {
            final var useSiteTarget = annotationUseSiteTargetContext.getText();
            annotationBuilder.useSiteTarget(toEnumUseSiteTarget(useSiteTarget));
        }
        elementBuilder.addAnnotation(annotationBuilder.build());
    }

    private static ElementAnnotation.@NonNull UseSiteTarget toEnumUseSiteTarget(final @NonNull String useSiteTarget) {
        // A use-site target is like '@get:', we want 'GET'
        final var cleaned = useSiteTarget.substring(1, useSiteTarget.length() - 1).toUpperCase(Locale.US);
        return ElementAnnotation.UseSiteTarget.valueOf(cleaned);
    }
}
