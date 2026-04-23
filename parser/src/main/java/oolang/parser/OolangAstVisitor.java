/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.parser;

import oolang.ast.Annotation;
import oolang.ast.Ast;
import oolang.ast.FileAst;
import oolang.ast.Identifier;
import oolang.ast.element.ClassBody;
import oolang.ast.element.ElementModifier;
import oolang.ast.element.RealElement;
import oolang.ast.expression.Expression;
import oolang.ast.expression.ExpressionNode;
import oolang.ast.expression.RealExpression;
import oolang.ast.expression.SimpleString;
import oolang.ast.statement.Block;
import oolang.ast.statement.RealStatement;
import oolang.ast.statement.Statement;
import oolang.parser.generated.OolangParser.*;
import oolang.parser.generated.OolangParserBaseVisitor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static oolang.ast.element.RealElement.ElementType.*;
import static oolang.ast.element.RealElement.ElementType.CLASS;
import static oolang.ast.element.RealElement.ElementType.CONSTRUCTOR;
import static oolang.ast.element.RealElement.ElementType.FUN;
import static oolang.ast.element.RealElement.ElementType.VAL;
import static oolang.ast.element.RealElement.ElementType.VAR;
import static oolang.ast.expression.RealExpression.ExpressionType.ARGUMENT;
import static oolang.ast.expression.RealExpression.ExpressionType.FUN_CALL;

public final class OolangAstVisitor extends OolangParserBaseVisitor<Ast> {
    @Override
    public @NonNull FileAst visitOolangFile(final @NonNull OolangFileContext ctx) {
        assert ctx != null;

        final var fileAstBuilder = new FileAst.Builder();
        for (final var topLevelObjectCtx : ctx.topLevelObject()) {
            fileAstBuilder.addRootElement(visitClassDeclaration(topLevelObjectCtx.classDeclaration()));
        }
        return fileAstBuilder.build();
    }

    @Override
    public @NonNull RealElement visitClassDeclaration(final @NonNull ClassDeclarationContext ctx) {
        assert ctx != null;

        final var classBuilder = new RealElement.Builder().elementType(CLASS);

        if (ctx.simpleIdentifier() != null) {
            final var classId = new Identifier.Builder(ctx.simpleIdentifier().getText()).build();
            classBuilder.identifier(classId);
        }

        addModifiersAndAnnotations(ctx.modifiers(), classBuilder);

        if (ctx.primaryConstructor() != null) {
            classBuilder.addChild(visitPrimaryConstructor(ctx.primaryConstructor()));
        }

        if (ctx.classBody() != null) {
            classBuilder.addChild(visitClassBody(ctx.classBody()));
        }

        return classBuilder.build();
    }

    @Override
    public @NonNull RealElement visitPrimaryConstructor(final @NonNull PrimaryConstructorContext ctx) {
        assert ctx != null;

        final var constructorBuilder = new RealElement.Builder().elementType(CONSTRUCTOR);
        addModifiersAndAnnotations(ctx.modifiers(), constructorBuilder);

        for (final var classParamCtx : ctx.classParameters().classParameter()) {
            constructorBuilder.addChild(visitClassParameter(classParamCtx));
        }

        return constructorBuilder.build();
    }

    @Override
    public @NonNull RealElement visitClassParameter(final @NonNull ClassParameterContext ctx) {
        assert ctx != null;

        final var classParamBuilder = new RealElement.Builder();
        if (ctx.VAL() != null) {
            classParamBuilder.elementType(VAL);
        } else if (ctx.VAR() != null) {
            classParamBuilder.elementType(VAR);
        } else {
            classParamBuilder.elementType(PARAMETER);
        }
        final var paramId = new Identifier.Builder(ctx.simpleIdentifier().getText()).build();
        classParamBuilder.identifier(paramId);
        final var paramTypeId = new Identifier.Builder(ctx.type().getText()).build();
        classParamBuilder.type(List.of(paramTypeId));
        addModifiersAndAnnotations(ctx.modifiers(), classParamBuilder);

        return classParamBuilder.build();
    }

    @Override
    public @NonNull ClassBody visitClassBody(final @NonNull ClassBodyContext ctx) {
        assert ctx != null;

        final var classBodyBuilder = new ClassBody.Builder();
        for (final var classMemberDeclarationCtx : ctx.classMemberDeclarations().classMemberDeclaration()) {
            classBodyBuilder.addChild(visitClassMemberDeclaration(classMemberDeclarationCtx));
        }
        return classBodyBuilder.build();
    }

    @Override
    public @NonNull RealElement visitClassMemberDeclaration(
            final @NonNull ClassMemberDeclarationContext ctx
    ) {
        assert ctx != null;

        if (ctx.declaration() != null) {
            return visitDeclaration(ctx.declaration());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RealElement visitDeclaration(final @NonNull DeclarationContext ctx) {
        assert ctx != null;

        if (ctx.functionDeclaration() != null) {
            return visitFunctionDeclaration(ctx.functionDeclaration());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RealElement visitFunctionDeclaration(final @NonNull FunctionDeclarationContext ctx) {
        assert ctx != null;

        final var functionBuilder = new RealElement.Builder().elementType(FUN);
        final var functionId = new Identifier.Builder(ctx.simpleIdentifier().getText()).build();
        functionBuilder.identifier(functionId);
        if (ctx.type() != null) {
            final var functionTypeId = new Identifier.Builder(ctx.type().getText()).build();
            functionBuilder.type(List.of(functionTypeId));
        }
        addModifiersAndAnnotations(ctx.modifiers(), functionBuilder);

        for (final var funParamCtx : ctx.functionValueParameters().functionValueParameter()) {
            functionBuilder.addChild(visitFunctionValueParameter(funParamCtx));
        }

        functionBuilder.addChild(visitFunctionBody(ctx.functionBody()));

        return functionBuilder.build();
    }

    @Override
    public @NonNull RealElement visitFunctionValueParameter(final @NonNull FunctionValueParameterContext ctx) {
        assert ctx != null;

        var funParamBuilder = new RealElement.Builder().elementType(PARAMETER);
        final var parameterCtx = ctx.parameter();
        final var paramId = new Identifier.Builder(parameterCtx.simpleIdentifier().getText()).build();
        funParamBuilder.identifier(paramId);
        final var paramTypeId = new Identifier.Builder(parameterCtx.type().getText()).build();
        funParamBuilder.type(List.of(paramTypeId));

        if (ctx.parameterModifiers() != null) {
            if (ctx.parameterModifiers().VARARG() != null) {
                funParamBuilder.addModifier(new ElementModifier(ElementModifier.ModifierType.PARAMETER, "vararg"));
            }
            funParamBuilder.annotations(visitAnnotations(ctx.parameterModifiers().annotation()));
        }

        return funParamBuilder.build();
    }

    @Override
    public @NonNull Block visitFunctionBody(final @NonNull FunctionBodyContext ctx) {
        assert ctx != null;

        final var functionBodyBuilder = new Block.Builder();
        if (ctx.block() != null) {
            for (final var statementCtx : ctx.block().statements().statement()) {
                functionBodyBuilder.addChild(visitStatement(statementCtx));
            }
        } else {
            Objects.requireNonNull(ctx.expression());
            throw new UnsupportedOperationException();
        }
        return functionBodyBuilder.build();
    }

    @Override
    public @NonNull Statement visitStatement(final @NonNull StatementContext ctx) {
        assert ctx != null;

        final var statementBuilder = new RealStatement.Builder();
        statementBuilder.annotations(visitAnnotations(ctx.annotation()));
        if (ctx.expression() != null) {
            statementBuilder.addChild(visitExpression(ctx.expression()));
        } else {
            throw new UnsupportedOperationException();
        }
        return statementBuilder.build();
    }

    @Override
    public @NonNull Expression visitExpression(final @NonNull ExpressionContext ctx) {
        assert ctx != null;

        for (final var conjunctionCtx : ctx.disjunction().conjunction()) {
            for (final var equalityCtx : conjunctionCtx.equality()) {
                for (final var comparisonCtx : equalityCtx.comparison()) {
                    for (final var genericCallLikeComparisonCtx : comparisonCtx.genericCallLikeComparison()) {
                        final var infixOperationCtx = genericCallLikeComparisonCtx.infixOperation();
                        for (final var elvisExpressionCtx : infixOperationCtx.elvisExpression()) {
                            for (final var infixFunctionCallCtx : elvisExpressionCtx.infixFunctionCall()) {
                                for (final var rangeExpressionCtx : infixFunctionCallCtx.rangeExpression()) {
                                    for (final var additiveExpressionCtx : rangeExpressionCtx.additiveExpression()) {
                                        for (final var multiplicativeExpressionCtx : additiveExpressionCtx.multiplicativeExpression()) {
                                            for (final var asExpressionCtx : multiplicativeExpressionCtx.asExpression()) {
                                                final var prefixUnaryExpressionCtx = asExpressionCtx.prefixUnaryExpression();
                                                return visitPostfixUnaryExpression(prefixUnaryExpressionCtx.postfixUnaryExpression());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Expression visitPostfixUnaryExpression(final @NonNull PostfixUnaryExpressionContext ctx) {
        assert ctx != null;

        final var primaryExpressionCtx = ctx.primaryExpression();

        if (primaryExpressionCtx.stringLiteral() != null) {
            return visitStringLiteral(primaryExpressionCtx.stringLiteral());
        }

        if (primaryExpressionCtx.simpleIdentifier() != null) {
            final var expressionBuilder = new RealExpression.Builder();
            expressionBuilder.addIdentifier(
                    new Identifier.Builder(primaryExpressionCtx.simpleIdentifier().getText()).build());
            return visitPostfixUnarySuffixes(ctx.postfixUnarySuffix(), expressionBuilder);
        }

        if (primaryExpressionCtx.literalConstant() != null) {
            System.out.println("literalConstant " + primaryExpressionCtx.literalConstant().getText());
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Expression visitStringLiteral(final @NonNull StringLiteralContext ctx) {
        if (ctx.lineStringLiteral() != null) {
            return visitLineStringLiteral(ctx.lineStringLiteral());
        }
        Objects.requireNonNull(ctx.multiLineStringLiteral());
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Expression visitLineStringLiteral(final @NonNull LineStringLiteralContext ctx) {
        assert ctx != null;

        // fast-path for single String content
        if (ctx.lineStringContent().size() == 1 && ctx.lineStringExpression().isEmpty()) {
            return new SimpleString(ctx.lineStringContent().getFirst().getText());
        }
//        final var expressionBuilder = new RealExpression.Builder().type(STRING_LITERAL);
        throw new UnsupportedOperationException();
    }

    private @NonNull RealExpression visitPostfixUnarySuffixes(
            final @NonNull List<@NonNull PostfixUnarySuffixContext> ctx,
            final RealExpression.@NonNull Builder expressionBuilder
    ) {
        for (final var postfixUnarySuffixCtx : ctx) {
            final var navSuffixCtx = postfixUnarySuffixCtx.navigationSuffix();
            if (navSuffixCtx != null) {
                if (navSuffixCtx.memberAccessOperator().DOT() != null) {
                    final var suffixId = navSuffixCtx.simpleIdentifier().getText();
                    expressionBuilder.addIdentifier(new Identifier.Builder(suffixId).build());
                }
            }
            if (postfixUnarySuffixCtx.typeArguments() != null) {
                System.out.println("typeArguments " + postfixUnarySuffixCtx.typeArguments().getText());
            }
            if (postfixUnarySuffixCtx.callSuffix() != null) {
                expressionBuilder.type(FUN_CALL);
                // add function arguments
                final var valueArgumentsCtx = postfixUnarySuffixCtx.callSuffix().valueArguments();
                if (valueArgumentsCtx != null) {
                    for (final var valueArgumentCtx : valueArgumentsCtx.valueArgument()) {
                        final var callArgumentBuilder = new RealExpression.Builder().type(ARGUMENT);
                        if (valueArgumentCtx.simpleIdentifier() != null) {
                            callArgumentBuilder.addIdentifier(
                                    new Identifier.Builder(valueArgumentCtx.simpleIdentifier().getText()).build());
                        }
                        if (valueArgumentCtx.annotation() != null) {
                            callArgumentBuilder.annotations(visitAnnotations(List.of(valueArgumentCtx.annotation())));
                        }
                        if (valueArgumentCtx.expression() != null) {
                            callArgumentBuilder.addChild(visitExpression(valueArgumentCtx.expression()));
                        }
                        expressionBuilder.addChild(callArgumentBuilder.build());
                    }
                }
                return expressionBuilder.build();
            }
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull ElementModifier visitModifier(final @NonNull ModifierContext ctx) {
        assert ctx != null;

        final ElementModifier.ModifierType type;
        if (ctx.visibilityModifier() != null) {
            type = ElementModifier.ModifierType.VISIBILITY;
        } else if (ctx.classModifier() != null) {
            type = ElementModifier.ModifierType.CLASS;
        } else if (ctx.VARARG() != null) {
            type = ElementModifier.ModifierType.PARAMETER;
        } else if (ctx.inheritanceModifier() != null) {
            type = ElementModifier.ModifierType.INHERITANCE;
        } else if (ctx.memberModifier() != null) {
            type = ElementModifier.ModifierType.MEMBER;
        } else {
            throw new IllegalStateException("Unknown modifier: " + ctx.getText());
        }
        return new ElementModifier(type, ctx.getText());
    }

    private void addModifiersAndAnnotations(final @Nullable ModifiersContext ctx,
                                            final RealElement.@NonNull Builder elementBuilder) {
        assert elementBuilder != null;
        if (ctx == null) {
            return;
        }

        for (final var modifierCtx : ctx.modifier()) {
            elementBuilder.addModifier(visitModifier(modifierCtx));
        }
        elementBuilder.annotations(visitAnnotations(ctx.annotation()));
    }

    private static @NonNull List<@NonNull Annotation> visitAnnotations(
            final @NonNull List<@NonNull AnnotationContext> annotationContexts) {
        assert annotationContexts != null;

        if (annotationContexts.isEmpty()) {
            return List.of();
        }

        final var annotations = new ArrayList<Annotation>();
        for (final var annotationCtx : annotationContexts) {
            if (annotationCtx.singleAnnotation() != null) {
                annotations.add(visitAnnotation(annotationCtx.singleAnnotation().unescapedAnnotation(),
                        annotationCtx.singleAnnotation().annotationUseSiteTarget()));
            } else if (annotationCtx.multiAnnotation() != null) {
                for (final var unescapedAnnotation : annotationCtx.multiAnnotation().unescapedAnnotation()) {
                    annotations.add(visitAnnotation(unescapedAnnotation,
                            annotationCtx.multiAnnotation().annotationUseSiteTarget()));
                }
            }
        }
        return annotations;
    }

    private static @NonNull Annotation visitAnnotation(
            final @NonNull UnescapedAnnotationContext unescapedAnnotationContext,
            final @Nullable AnnotationUseSiteTargetContext annotationUseSiteTargetContext
    ) {
        assert unescapedAnnotationContext != null;

        final var annotationBuilder = new Annotation.Builder();
        final var annotationIdentifier = new Identifier.Builder(unescapedAnnotationContext.getText()).build();
        annotationBuilder.addIdentifier(annotationIdentifier);
        if (annotationUseSiteTargetContext != null) {
            final var useSiteTarget = annotationUseSiteTargetContext.getText();
            annotationBuilder.useSiteTarget(toEnumUseSiteTarget(useSiteTarget));
        }
        return annotationBuilder.build();
    }

    private static Annotation.@NonNull UseSiteTarget toEnumUseSiteTarget(final @NonNull String useSiteTarget) {
        // A use-site target is like '@get:', we want 'GET'
        final var cleaned = useSiteTarget.substring(1, useSiteTarget.length() - 1).toUpperCase(Locale.US);
        return Annotation.UseSiteTarget.valueOf(cleaned);
    }
}
