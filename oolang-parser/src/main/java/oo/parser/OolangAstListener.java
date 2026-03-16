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

    public OolangAstListener() {
        stack.push(new ElementDeclaration.Builder());
    }

    public @NonNull FileAst fileAst() {
        if (fileAst == null) {
            throw new IllegalStateException("fileAst is not built yet");
        }
        return fileAst;
    }

    @Override
    public void exitSingleAnnotation(final OolangParser.@NonNull SingleAnnotationContext ctx) {
        assert ctx != null;

        addAnnotation(ctx.unescapedAnnotation(), ctx.annotationUseSiteTarget());
    }

    @Override
    public void exitMultiAnnotation(final OolangParser.@NonNull MultiAnnotationContext ctx) {
        assert ctx != null;

        for (final var unescapedAnnotation : ctx.unescapedAnnotation()) {
            addAnnotation(unescapedAnnotation, ctx.annotationUseSiteTarget());
        }
    }

    private void addAnnotation(
            final OolangParser.@NonNull UnescapedAnnotationContext unescapedAnnotationContext,
            final OolangParser.@Nullable AnnotationUseSiteTargetContext annotationUseSiteTargetContext
    ) {
        assert unescapedAnnotationContext != null;

        final var builder = stack.element();
        final var annotationIdentifier = new ElementIdentifier.Builder(unescapedAnnotationContext.getText()).build();
        final var annotationBuilder = new ElementAnnotation.Builder(List.of(annotationIdentifier));
        if (annotationUseSiteTargetContext != null) {
            final var useSiteTarget = annotationUseSiteTargetContext.getText();
            annotationBuilder.useSiteTarget(toEnumUseSiteTarget(useSiteTarget));
        }
        builder.addAnnotation(annotationBuilder.build());
    }

    private static ElementAnnotation.@NonNull UseSiteTarget toEnumUseSiteTarget(final @NonNull String useSiteTarget) {
        // A use-site target is like '@get:', we want 'GET'
        final var cleaned = useSiteTarget.substring(1, useSiteTarget.length() - 1).toUpperCase(Locale.US);
        return ElementAnnotation.UseSiteTarget.valueOf(cleaned);
    }

    @Override
    public void exitModifier(final OolangParser.@NonNull ModifierContext ctx) {
        assert ctx != null;

        final var builder = stack.element();
        final ElementModifier.ModifierType group;
        if (ctx.visibilityModifier() != null) {
            group = ElementModifier.ModifierType.VISIBILITY;
        } else if (ctx.classModifier() != null) {
            group = ElementModifier.ModifierType.CLASS;
        } else if (ctx.parameterModifier() != null) {
            group = ElementModifier.ModifierType.PARAMETER;
        } else if (ctx.inheritanceModifier() != null) {
            group = ElementModifier.ModifierType.INHERITANCE;
        } else if (ctx.memberModifier() != null) {
            group = ElementModifier.ModifierType.MEMBER;
        } else {
            throw new IllegalStateException("Unknown modifier: " + ctx.getText());
        }
        builder.addModifier(new ElementModifier(ctx.getText(), group));
    }

    @Override
    public void exitPrimaryConstructor(final OolangParser.@NonNull PrimaryConstructorContext ctx) {
        assert ctx != null;

        final var constructorBuilder = new ElementDeclaration.Builder()
                .elementType(CONSTRUCTOR);
        for (final var classParamContext : ctx.classParameters().classParameter()) {
            // add each parameter to the constructor
            final var paramBuilder = new ElementDeclaration.Builder();
            if (classParamContext.VAL() != null) {
                paramBuilder.elementType(VAL);
            } else if (classParamContext.VAR() != null) {
                paramBuilder.elementType(VAR);
            } else {
                paramBuilder.elementType(PARAMETER);
            }
            final var paramId = new ElementIdentifier.Builder(classParamContext.simpleIdentifier().getText()).build();
            paramBuilder.identifier(paramId);
            final var paramTypeId = new ElementIdentifier.Builder(classParamContext.type().getText()).build();
            paramBuilder.type(List.of(paramTypeId));
            constructorBuilder.addChild(paramBuilder.build());
        }
        // add constructor to the current class declaration
        final var builder = stack.element();
        builder.addChild(constructorBuilder.build());
    }

    @Override
    public void exitClassDeclaration(final OolangParser.@NonNull ClassDeclarationContext ctx) {
        assert ctx != null;

        final var builder = stack.pop();
        if (ctx.simpleIdentifier() != null) {
            final var classId = new ElementIdentifier.Builder(ctx.simpleIdentifier().getText()).build();
            builder.identifier(classId);
        }
        final var element = builder.elementType(CLASS).build();

        final var parent = stack.peek();
        if (parent != null) {
            parent.addChild(element);
        } else {
            fileAstBuilder.addRootElement(element);
        }
        stack.push(new ElementDeclaration.Builder());
    }

    @Override
    public void exitOolangFile(final OolangParser.@NonNull OolangFileContext ctx) {
        assert ctx != null;

        this.fileAst = fileAstBuilder.build();
    }
}
