/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.parser;

import oolang.ast.*;
import oolang.ast.element.ClassBody;
import oolang.ast.element.ElementModifier;
import oolang.ast.element.RealElement;
import oolang.ast.expression.Expression;
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

        final var fileAst = new FileAst();
        for (final var topLevelObjectCtx : ctx.topLevelObject()) {
            fileAst.rootElements.add(visitClassDeclaration(topLevelObjectCtx.classDeclaration()));
        }
        return fileAst;
    }

    @Override
    public @NonNull RealElement visitClassDeclaration(final @NonNull ClassDeclarationContext ctx) {
        assert ctx != null;

        final var clazz = new RealElement(CLASS);

        if (ctx.simpleIdentifier() != null) {
            clazz.identifier = visitSimpleIdentifier(ctx.simpleIdentifier());
        }

        addModifiersAndAnnotations(ctx.modifiers(), clazz);

        if (ctx.primaryConstructor() != null) {
            clazz.children.add(visitPrimaryConstructor(ctx.primaryConstructor()));
        }

        if (ctx.classBody() != null) {
            clazz.children.add(visitClassBody(ctx.classBody()));
        }

        return clazz;
    }

    @Override
    public @NonNull RealElement visitPrimaryConstructor(final @NonNull PrimaryConstructorContext ctx) {
        assert ctx != null;

        final var constructor = new RealElement(CONSTRUCTOR);
        addModifiersAndAnnotations(ctx.modifiers(), constructor);

        for (final var classParamCtx : ctx.classParameters().classParameter()) {
            constructor.children.add(visitClassParameter(classParamCtx));
        }

        return constructor;
    }

    @Override
    public @NonNull RealElement visitClassParameter(final @NonNull ClassParameterContext ctx) {
        assert ctx != null;

        final RealElement classParam;
        if (ctx.VAL() != null) {
            classParam = new RealElement(VAL);
        } else if (ctx.VAR() != null) {
            classParam = new RealElement(VAR);
        } else {
            classParam = new RealElement(PARAMETER);
        }
        classParam.identifier = visitSimpleIdentifier(ctx.simpleIdentifier());

        classParam.type = visitType(ctx.type());
        addModifiersAndAnnotations(ctx.modifiers(), classParam);

        return classParam;
    }

    @Override
    public @NonNull ClassBody visitClassBody(final @NonNull ClassBodyContext ctx) {
        assert ctx != null;

        final var classBody = new ClassBody();
        for (final var classMemberDeclarationCtx : ctx.classMemberDeclarations().classMemberDeclaration()) {
            classBody.children.add(visitClassMemberDeclaration(classMemberDeclarationCtx));
        }
        return classBody;
    }

    @Override
    public @NonNull RealElement visitClassMemberDeclaration(final @NonNull ClassMemberDeclarationContext ctx) {
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

        final var function = new RealElement(FUN);
        function.identifier = visitSimpleIdentifier(ctx.simpleIdentifier());
        if (ctx.type() != null) {
            function.type = visitType(ctx.type());
        }
        addModifiersAndAnnotations(ctx.modifiers(), function);

        for (final var funParamCtx : ctx.functionValueParameters().functionValueParameter()) {
            function.children.add(visitFunctionValueParameter(funParamCtx));
        }

        function.children.add(visitFunctionBody(ctx.functionBody()));

        return function;
    }

    @Override
    public @NonNull RealElement visitFunctionValueParameter(final @NonNull FunctionValueParameterContext ctx) {
        assert ctx != null;

        var funParam = new RealElement(PARAMETER);
        final var parameterCtx = ctx.parameter();
        funParam.identifier = visitSimpleIdentifier(parameterCtx.simpleIdentifier());
        funParam.type = visitType(parameterCtx.type());

        if (ctx.parameterModifiers() != null) {
            if (ctx.parameterModifiers().VARARG() != null) {
                funParam.modifiers.add(new ElementModifier(ElementModifier.ModifierType.PARAMETER, "vararg"));
            }
            funParam.annotations = visitAnnotations(ctx.parameterModifiers().annotation());
        }

        return funParam;
    }

    @Override
    public @NonNull Block visitFunctionBody(final @NonNull FunctionBodyContext ctx) {
        assert ctx != null;

        final var functionBody = new Block();
        if (ctx.block() != null) {
            for (final var statementCtx : ctx.block().statements().statement()) {
                functionBody.children.add(visitStatement(statementCtx));
            }
        } else {
            Objects.requireNonNull(ctx.expression());
            throw new UnsupportedOperationException();
        }
        return functionBody;
    }

    @Override
    public @NonNull Statement visitStatement(final @NonNull StatementContext ctx) {
        assert ctx != null;

        final var statement = new RealStatement();
        statement.annotations = visitAnnotations(ctx.annotation());
        if (ctx.expression() != null) {
            statement.children.add(visitExpression(ctx.expression()));
        } else {
            throw new UnsupportedOperationException();
        }
        return statement;
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
            final var expression = new RealExpression();
            expression.identifiers.add(visitSimpleIdentifier(primaryExpressionCtx.simpleIdentifier()));
            return visitPostfixUnarySuffixes(ctx.postfixUnarySuffix(), expression);
        }

        if (primaryExpressionCtx.literalConstant() != null) {
            System.out.println("literalConstant " + primaryExpressionCtx.literalConstant().getText());
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Expression visitStringLiteral(final @NonNull StringLiteralContext ctx) {
        assert ctx != null;

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
//        final var expression = new RealExpression(STRING_LITERAL);
        throw new UnsupportedOperationException();
    }

    private @NonNull RealExpression visitPostfixUnarySuffixes(
            final @NonNull List<@NonNull PostfixUnarySuffixContext> ctx,
            final @NonNull RealExpression expression
    ) {
        assert ctx != null;
        assert expression != null;

        for (final var postfixUnarySuffixCtx : ctx) {
            final var navSuffixCtx = postfixUnarySuffixCtx.navigationSuffix();
            if (navSuffixCtx != null) {
                if (navSuffixCtx.memberAccessOperator().DOT() != null) {
                    expression.identifiers.add(visitSimpleIdentifier(navSuffixCtx.simpleIdentifier()));
                }
            }
            if (postfixUnarySuffixCtx.typeArguments() != null) {
                System.out.println("typeArguments " + postfixUnarySuffixCtx.typeArguments().getText());
            }
            if (postfixUnarySuffixCtx.callSuffix() != null) {
                expression.type = FUN_CALL;
                // add function arguments
                final var valueArgsCtx = postfixUnarySuffixCtx.callSuffix().valueArguments();
                if (valueArgsCtx != null) {
                    for (final var valueArgCtx : valueArgsCtx.valueArgument()) {
                        final var callArgument = new RealExpression(ARGUMENT);
                        if (valueArgCtx.simpleIdentifier() != null) {
                            callArgument.identifiers.add(visitSimpleIdentifier(valueArgCtx.simpleIdentifier()));
                        }
                        if (valueArgCtx.annotation() != null) {
                            callArgument.annotations = visitAnnotations(List.of(valueArgCtx.annotation()));
                        }
                        if (valueArgCtx.expression() != null) {
                            callArgument.children.add(visitExpression(valueArgCtx.expression()));
                        }
                        expression.children.add(callArgument);
                    }
                }
                return expression;
            }
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Type visitType(final @NonNull TypeContext ctx) {
        assert ctx != null;

        final Type type;
        if (ctx.userType() != null) {
            type = visitUserType(ctx.userType());
        } else {
            throw new UnsupportedOperationException();
        }
        if (ctx.annotations() != null) {
            type.annotations = visitAnnotations(ctx.annotations().annotation());
        }
        return type;
    }

    @Override
    public @NonNull Type visitUserType(final @NonNull UserTypeContext ctx) {
        assert ctx != null;

        final var type = new Type();
        for (final var simpleUserTypeCtx : ctx.simpleUserType()) {
            type.identifiers.add(visitSimpleUserType(simpleUserTypeCtx));
        }
        return type;
    }

    @Override
    public @NonNull Identifier visitSimpleUserType(final @NonNull SimpleUserTypeContext ctx) {
        assert ctx != null;

        final var identifier = visitSimpleIdentifier(ctx.simpleIdentifier());
        if (ctx.typeArguments() != null) {
            for (final var typeProjectionCtx : ctx.typeArguments().typeProjection()) {
                identifier.parameters.add(visitTypeProjection(typeProjectionCtx));
            }
        }

        return identifier;
    }

    @Override
    public @NonNull Identifier visitSimpleIdentifier(final @NonNull SimpleIdentifierContext ctx) {
        assert ctx != null;
        return new Identifier(ctx.getText());
    }

    @Override
    public @NonNull Type visitTypeProjection(final @NonNull TypeProjectionContext ctx) {
        assert ctx != null;

        final var type = visitType(ctx.type());
        if (ctx.typeProjectionModifiers() != null) {
            throw new UnsupportedOperationException();
        }
        return type;
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

    private void addModifiersAndAnnotations(final @Nullable ModifiersContext ctx, final @NonNull RealElement element) {
        assert element != null;
        if (ctx == null) {
            return;
        }

        for (final var modifierCtx : ctx.modifier()) {
            element.modifiers.add(visitModifier(modifierCtx));
        }
        element.annotations = visitAnnotations(ctx.annotation());
    }

    private @NonNull List<@NonNull Annotation> visitAnnotations(
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
        return annotations.isEmpty() ? List.of() : List.copyOf(annotations);
    }

    private @NonNull Annotation visitAnnotation(
            final @NonNull UnescapedAnnotationContext unescapedAnnotationContext,
            final @Nullable AnnotationUseSiteTargetContext annotationUseSiteTargetContext
    ) {
        assert unescapedAnnotationContext != null;

        final var annotation = new Annotation(visitUserType(unescapedAnnotationContext.userType()));
        if (annotationUseSiteTargetContext != null) {
            final var useSiteTarget = annotationUseSiteTargetContext.getText();
            annotation.useSiteTarget = toEnumUseSiteTarget(useSiteTarget);
        }
        return annotation;
    }

    private static Annotation.@NonNull UseSiteTarget toEnumUseSiteTarget(final @NonNull String useSiteTarget) {
        // A use-site target is like '@get:', we want 'GET'
        final var cleaned = useSiteTarget.substring(1, useSiteTarget.length() - 1).toUpperCase(Locale.US);
        return Annotation.UseSiteTarget.valueOf(cleaned);
    }
}
