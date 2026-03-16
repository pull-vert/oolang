// Generated from OolangParser.g4 by ANTLR 4.13.2
package oo.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link OolangParser}.
 */
public interface OolangParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link OolangParser#oolangFile}.
	 * @param ctx the parse tree
	 */
	void enterOolangFile(OolangParser.OolangFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#oolangFile}.
	 * @param ctx the parse tree
	 */
	void exitOolangFile(OolangParser.OolangFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void enterPackageHeader(OolangParser.PackageHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void exitPackageHeader(OolangParser.PackageHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#importList}.
	 * @param ctx the parse tree
	 */
	void enterImportList(OolangParser.ImportListContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#importList}.
	 * @param ctx the parse tree
	 */
	void exitImportList(OolangParser.ImportListContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void enterImportHeader(OolangParser.ImportHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void exitImportHeader(OolangParser.ImportHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#importAlias}.
	 * @param ctx the parse tree
	 */
	void enterImportAlias(OolangParser.ImportAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#importAlias}.
	 * @param ctx the parse tree
	 */
	void exitImportAlias(OolangParser.ImportAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#topLevelObject}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelObject(OolangParser.TopLevelObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#topLevelObject}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelObject(OolangParser.TopLevelObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(OolangParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(OolangParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(OolangParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(OolangParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#primaryConstructor}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryConstructor(OolangParser.PrimaryConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#primaryConstructor}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryConstructor(OolangParser.PrimaryConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(OolangParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(OolangParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#classParameters}.
	 * @param ctx the parse tree
	 */
	void enterClassParameters(OolangParser.ClassParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#classParameters}.
	 * @param ctx the parse tree
	 */
	void exitClassParameters(OolangParser.ClassParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#classParameter}.
	 * @param ctx the parse tree
	 */
	void enterClassParameter(OolangParser.ClassParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#classParameter}.
	 * @param ctx the parse tree
	 */
	void exitClassParameter(OolangParser.ClassParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#delegationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void enterDelegationSpecifiers(OolangParser.DelegationSpecifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#delegationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void exitDelegationSpecifiers(OolangParser.DelegationSpecifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#delegationSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDelegationSpecifier(OolangParser.DelegationSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#delegationSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDelegationSpecifier(OolangParser.DelegationSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#constructorInvocation}.
	 * @param ctx the parse tree
	 */
	void enterConstructorInvocation(OolangParser.ConstructorInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#constructorInvocation}.
	 * @param ctx the parse tree
	 */
	void exitConstructorInvocation(OolangParser.ConstructorInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#annotatedDelegationSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAnnotatedDelegationSpecifier(OolangParser.AnnotatedDelegationSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#annotatedDelegationSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAnnotatedDelegationSpecifier(OolangParser.AnnotatedDelegationSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#explicitDelegation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitDelegation(OolangParser.ExplicitDelegationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#explicitDelegation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitDelegation(OolangParser.ExplicitDelegationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(OolangParser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(OolangParser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(OolangParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(OolangParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeConstraints}.
	 * @param ctx the parse tree
	 */
	void enterTypeConstraints(OolangParser.TypeConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeConstraints}.
	 * @param ctx the parse tree
	 */
	void exitTypeConstraints(OolangParser.TypeConstraintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void enterTypeConstraint(OolangParser.TypeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void exitTypeConstraint(OolangParser.TypeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#classMemberDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterClassMemberDeclarations(OolangParser.ClassMemberDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#classMemberDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitClassMemberDeclarations(OolangParser.ClassMemberDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassMemberDeclaration(OolangParser.ClassMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassMemberDeclaration(OolangParser.ClassMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#anonymousInitializer}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousInitializer(OolangParser.AnonymousInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#anonymousInitializer}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousInitializer(OolangParser.AnonymousInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionValueParameters}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValueParameters(OolangParser.FunctionValueParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionValueParameters}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValueParameters(OolangParser.FunctionValueParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionValueParameter}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValueParameter(OolangParser.FunctionValueParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionValueParameter}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValueParameter(OolangParser.FunctionValueParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(OolangParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(OolangParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(OolangParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(OolangParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(OolangParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(OolangParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#multiVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMultiVariableDeclaration(OolangParser.MultiVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#multiVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMultiVariableDeclaration(OolangParser.MultiVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPropertyDeclaration(OolangParser.PropertyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPropertyDeclaration(OolangParser.PropertyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#propertyDelegate}.
	 * @param ctx the parse tree
	 */
	void enterPropertyDelegate(OolangParser.PropertyDelegateContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#propertyDelegate}.
	 * @param ctx the parse tree
	 */
	void exitPropertyDelegate(OolangParser.PropertyDelegateContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#getter}.
	 * @param ctx the parse tree
	 */
	void enterGetter(OolangParser.GetterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#getter}.
	 * @param ctx the parse tree
	 */
	void exitGetter(OolangParser.GetterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#setter}.
	 * @param ctx the parse tree
	 */
	void enterSetter(OolangParser.SetterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#setter}.
	 * @param ctx the parse tree
	 */
	void exitSetter(OolangParser.SetterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parametersWithOptionalType}.
	 * @param ctx the parse tree
	 */
	void enterParametersWithOptionalType(OolangParser.ParametersWithOptionalTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parametersWithOptionalType}.
	 * @param ctx the parse tree
	 */
	void exitParametersWithOptionalType(OolangParser.ParametersWithOptionalTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionValueParameterWithOptionalType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValueParameterWithOptionalType(OolangParser.FunctionValueParameterWithOptionalTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionValueParameterWithOptionalType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValueParameterWithOptionalType(OolangParser.FunctionValueParameterWithOptionalTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parameterWithOptionalType}.
	 * @param ctx the parse tree
	 */
	void enterParameterWithOptionalType(OolangParser.ParameterWithOptionalTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parameterWithOptionalType}.
	 * @param ctx the parse tree
	 */
	void exitParameterWithOptionalType(OolangParser.ParameterWithOptionalTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(OolangParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(OolangParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#secondaryConstructor}.
	 * @param ctx the parse tree
	 */
	void enterSecondaryConstructor(OolangParser.SecondaryConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#secondaryConstructor}.
	 * @param ctx the parse tree
	 */
	void exitSecondaryConstructor(OolangParser.SecondaryConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#constructorDelegationCall}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDelegationCall(OolangParser.ConstructorDelegationCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#constructorDelegationCall}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDelegationCall(OolangParser.ConstructorDelegationCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#enumClassBody}.
	 * @param ctx the parse tree
	 */
	void enterEnumClassBody(OolangParser.EnumClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#enumClassBody}.
	 * @param ctx the parse tree
	 */
	void exitEnumClassBody(OolangParser.EnumClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#enumEntries}.
	 * @param ctx the parse tree
	 */
	void enterEnumEntries(OolangParser.EnumEntriesContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#enumEntries}.
	 * @param ctx the parse tree
	 */
	void exitEnumEntries(OolangParser.EnumEntriesContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#enumEntry}.
	 * @param ctx the parse tree
	 */
	void enterEnumEntry(OolangParser.EnumEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#enumEntry}.
	 * @param ctx the parse tree
	 */
	void exitEnumEntry(OolangParser.EnumEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(OolangParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(OolangParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#nullableType}.
	 * @param ctx the parse tree
	 */
	void enterNullableType(OolangParser.NullableTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#nullableType}.
	 * @param ctx the parse tree
	 */
	void exitNullableType(OolangParser.NullableTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#quest}.
	 * @param ctx the parse tree
	 */
	void enterQuest(OolangParser.QuestContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#quest}.
	 * @param ctx the parse tree
	 */
	void exitQuest(OolangParser.QuestContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#userType}.
	 * @param ctx the parse tree
	 */
	void enterUserType(OolangParser.UserTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#userType}.
	 * @param ctx the parse tree
	 */
	void exitUserType(OolangParser.UserTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#simpleUserType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleUserType(OolangParser.SimpleUserTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#simpleUserType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleUserType(OolangParser.SimpleUserTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeProjection}.
	 * @param ctx the parse tree
	 */
	void enterTypeProjection(OolangParser.TypeProjectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeProjection}.
	 * @param ctx the parse tree
	 */
	void exitTypeProjection(OolangParser.TypeProjectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeProjectionModifiers}.
	 * @param ctx the parse tree
	 */
	void enterTypeProjectionModifiers(OolangParser.TypeProjectionModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeProjectionModifiers}.
	 * @param ctx the parse tree
	 */
	void exitTypeProjectionModifiers(OolangParser.TypeProjectionModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeProjectionModifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeProjectionModifier(OolangParser.TypeProjectionModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeProjectionModifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeProjectionModifier(OolangParser.TypeProjectionModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(OolangParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(OolangParser.FunctionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionTypeParameters}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTypeParameters(OolangParser.FunctionTypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionTypeParameters}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTypeParameters(OolangParser.FunctionTypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parenthesizedType}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedType(OolangParser.ParenthesizedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parenthesizedType}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedType(OolangParser.ParenthesizedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#receiverType}.
	 * @param ctx the parse tree
	 */
	void enterReceiverType(OolangParser.ReceiverTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#receiverType}.
	 * @param ctx the parse tree
	 */
	void exitReceiverType(OolangParser.ReceiverTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parenthesizedUserType}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedUserType(OolangParser.ParenthesizedUserTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parenthesizedUserType}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedUserType(OolangParser.ParenthesizedUserTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#definitelyNonNullableType}.
	 * @param ctx the parse tree
	 */
	void enterDefinitelyNonNullableType(OolangParser.DefinitelyNonNullableTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#definitelyNonNullableType}.
	 * @param ctx the parse tree
	 */
	void exitDefinitelyNonNullableType(OolangParser.DefinitelyNonNullableTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(OolangParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(OolangParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(OolangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(OolangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(OolangParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(OolangParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#controlStructureBody}.
	 * @param ctx the parse tree
	 */
	void enterControlStructureBody(OolangParser.ControlStructureBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#controlStructureBody}.
	 * @param ctx the parse tree
	 */
	void exitControlStructureBody(OolangParser.ControlStructureBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(OolangParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(OolangParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(OolangParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(OolangParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(OolangParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(OolangParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(OolangParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(OolangParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(OolangParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(OolangParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#semi}.
	 * @param ctx the parse tree
	 */
	void enterSemi(OolangParser.SemiContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#semi}.
	 * @param ctx the parse tree
	 */
	void exitSemi(OolangParser.SemiContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#semis}.
	 * @param ctx the parse tree
	 */
	void enterSemis(OolangParser.SemisContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#semis}.
	 * @param ctx the parse tree
	 */
	void exitSemis(OolangParser.SemisContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(OolangParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(OolangParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(OolangParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(OolangParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(OolangParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(OolangParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#equality}.
	 * @param ctx the parse tree
	 */
	void enterEquality(OolangParser.EqualityContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#equality}.
	 * @param ctx the parse tree
	 */
	void exitEquality(OolangParser.EqualityContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(OolangParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(OolangParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#genericCallLikeComparison}.
	 * @param ctx the parse tree
	 */
	void enterGenericCallLikeComparison(OolangParser.GenericCallLikeComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#genericCallLikeComparison}.
	 * @param ctx the parse tree
	 */
	void exitGenericCallLikeComparison(OolangParser.GenericCallLikeComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#infixOperation}.
	 * @param ctx the parse tree
	 */
	void enterInfixOperation(OolangParser.InfixOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#infixOperation}.
	 * @param ctx the parse tree
	 */
	void exitInfixOperation(OolangParser.InfixOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#elvisExpression}.
	 * @param ctx the parse tree
	 */
	void enterElvisExpression(OolangParser.ElvisExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#elvisExpression}.
	 * @param ctx the parse tree
	 */
	void exitElvisExpression(OolangParser.ElvisExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#elvis}.
	 * @param ctx the parse tree
	 */
	void enterElvis(OolangParser.ElvisContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#elvis}.
	 * @param ctx the parse tree
	 */
	void exitElvis(OolangParser.ElvisContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterInfixFunctionCall(OolangParser.InfixFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitInfixFunctionCall(OolangParser.InfixFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#rangeExpression}.
	 * @param ctx the parse tree
	 */
	void enterRangeExpression(OolangParser.RangeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#rangeExpression}.
	 * @param ctx the parse tree
	 */
	void exitRangeExpression(OolangParser.RangeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(OolangParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(OolangParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(OolangParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(OolangParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#asExpression}.
	 * @param ctx the parse tree
	 */
	void enterAsExpression(OolangParser.AsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#asExpression}.
	 * @param ctx the parse tree
	 */
	void exitAsExpression(OolangParser.AsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryExpression(OolangParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryExpression(OolangParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#unaryPrefix}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPrefix(OolangParser.UnaryPrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#unaryPrefix}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPrefix(OolangParser.UnaryPrefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryExpression(OolangParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryExpression(OolangParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#postfixUnarySuffix}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnarySuffix(OolangParser.PostfixUnarySuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#postfixUnarySuffix}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnarySuffix(OolangParser.PostfixUnarySuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#directlyAssignableExpression}.
	 * @param ctx the parse tree
	 */
	void enterDirectlyAssignableExpression(OolangParser.DirectlyAssignableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#directlyAssignableExpression}.
	 * @param ctx the parse tree
	 */
	void exitDirectlyAssignableExpression(OolangParser.DirectlyAssignableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parenthesizedDirectlyAssignableExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedDirectlyAssignableExpression(OolangParser.ParenthesizedDirectlyAssignableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parenthesizedDirectlyAssignableExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedDirectlyAssignableExpression(OolangParser.ParenthesizedDirectlyAssignableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#assignableExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignableExpression(OolangParser.AssignableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#assignableExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignableExpression(OolangParser.AssignableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parenthesizedAssignableExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedAssignableExpression(OolangParser.ParenthesizedAssignableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parenthesizedAssignableExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedAssignableExpression(OolangParser.ParenthesizedAssignableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#assignableSuffix}.
	 * @param ctx the parse tree
	 */
	void enterAssignableSuffix(OolangParser.AssignableSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#assignableSuffix}.
	 * @param ctx the parse tree
	 */
	void exitAssignableSuffix(OolangParser.AssignableSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#indexingSuffix}.
	 * @param ctx the parse tree
	 */
	void enterIndexingSuffix(OolangParser.IndexingSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#indexingSuffix}.
	 * @param ctx the parse tree
	 */
	void exitIndexingSuffix(OolangParser.IndexingSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#navigationSuffix}.
	 * @param ctx the parse tree
	 */
	void enterNavigationSuffix(OolangParser.NavigationSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#navigationSuffix}.
	 * @param ctx the parse tree
	 */
	void exitNavigationSuffix(OolangParser.NavigationSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void enterCallSuffix(OolangParser.CallSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void exitCallSuffix(OolangParser.CallSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#annotatedLambda}.
	 * @param ctx the parse tree
	 */
	void enterAnnotatedLambda(OolangParser.AnnotatedLambdaContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#annotatedLambda}.
	 * @param ctx the parse tree
	 */
	void exitAnnotatedLambda(OolangParser.AnnotatedLambdaContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(OolangParser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(OolangParser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void enterValueArguments(OolangParser.ValueArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void exitValueArguments(OolangParser.ValueArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void enterValueArgument(OolangParser.ValueArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void exitValueArgument(OolangParser.ValueArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(OolangParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(OolangParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(OolangParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(OolangParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#collectionLiteral}.
	 * @param ctx the parse tree
	 */
	void enterCollectionLiteral(OolangParser.CollectionLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#collectionLiteral}.
	 * @param ctx the parse tree
	 */
	void exitCollectionLiteral(OolangParser.CollectionLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void enterLiteralConstant(OolangParser.LiteralConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void exitLiteralConstant(OolangParser.LiteralConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(OolangParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(OolangParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterLineStringLiteral(OolangParser.LineStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitLineStringLiteral(OolangParser.LineStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#multiLineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterMultiLineStringLiteral(OolangParser.MultiLineStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#multiLineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitMultiLineStringLiteral(OolangParser.MultiLineStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#lineStringContent}.
	 * @param ctx the parse tree
	 */
	void enterLineStringContent(OolangParser.LineStringContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#lineStringContent}.
	 * @param ctx the parse tree
	 */
	void exitLineStringContent(OolangParser.LineStringContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#lineStringExpression}.
	 * @param ctx the parse tree
	 */
	void enterLineStringExpression(OolangParser.LineStringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#lineStringExpression}.
	 * @param ctx the parse tree
	 */
	void exitLineStringExpression(OolangParser.LineStringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#multiLineStringContent}.
	 * @param ctx the parse tree
	 */
	void enterMultiLineStringContent(OolangParser.MultiLineStringContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#multiLineStringContent}.
	 * @param ctx the parse tree
	 */
	void exitMultiLineStringContent(OolangParser.MultiLineStringContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#multiLineStringExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiLineStringExpression(OolangParser.MultiLineStringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#multiLineStringExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiLineStringExpression(OolangParser.MultiLineStringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#lambdaLiteral}.
	 * @param ctx the parse tree
	 */
	void enterLambdaLiteral(OolangParser.LambdaLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#lambdaLiteral}.
	 * @param ctx the parse tree
	 */
	void exitLambdaLiteral(OolangParser.LambdaLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void enterLambdaParameters(OolangParser.LambdaParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void exitLambdaParameters(OolangParser.LambdaParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#lambdaParameter}.
	 * @param ctx the parse tree
	 */
	void enterLambdaParameter(OolangParser.LambdaParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#lambdaParameter}.
	 * @param ctx the parse tree
	 */
	void exitLambdaParameter(OolangParser.LambdaParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#anonymousFunction}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousFunction(OolangParser.AnonymousFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#anonymousFunction}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousFunction(OolangParser.AnonymousFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#functionLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFunctionLiteral(OolangParser.FunctionLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#functionLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFunctionLiteral(OolangParser.FunctionLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#thisExpression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpression(OolangParser.ThisExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#thisExpression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpression(OolangParser.ThisExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#superExpression}.
	 * @param ctx the parse tree
	 */
	void enterSuperExpression(OolangParser.SuperExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#superExpression}.
	 * @param ctx the parse tree
	 */
	void exitSuperExpression(OolangParser.SuperExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfExpression(OolangParser.IfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfExpression(OolangParser.IfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#whenSubject}.
	 * @param ctx the parse tree
	 */
	void enterWhenSubject(OolangParser.WhenSubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#whenSubject}.
	 * @param ctx the parse tree
	 */
	void exitWhenSubject(OolangParser.WhenSubjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhenExpression(OolangParser.WhenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhenExpression(OolangParser.WhenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#whenEntry}.
	 * @param ctx the parse tree
	 */
	void enterWhenEntry(OolangParser.WhenEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#whenEntry}.
	 * @param ctx the parse tree
	 */
	void exitWhenEntry(OolangParser.WhenEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#whenCondition}.
	 * @param ctx the parse tree
	 */
	void enterWhenCondition(OolangParser.WhenConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#whenCondition}.
	 * @param ctx the parse tree
	 */
	void exitWhenCondition(OolangParser.WhenConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#rangeTest}.
	 * @param ctx the parse tree
	 */
	void enterRangeTest(OolangParser.RangeTestContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#rangeTest}.
	 * @param ctx the parse tree
	 */
	void exitRangeTest(OolangParser.RangeTestContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeTest}.
	 * @param ctx the parse tree
	 */
	void enterTypeTest(OolangParser.TypeTestContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeTest}.
	 * @param ctx the parse tree
	 */
	void exitTypeTest(OolangParser.TypeTestContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#tryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTryExpression(OolangParser.TryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#tryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTryExpression(OolangParser.TryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void enterCatchBlock(OolangParser.CatchBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void exitCatchBlock(OolangParser.CatchBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void enterFinallyBlock(OolangParser.FinallyBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void exitFinallyBlock(OolangParser.FinallyBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#jumpExpression}.
	 * @param ctx the parse tree
	 */
	void enterJumpExpression(OolangParser.JumpExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#jumpExpression}.
	 * @param ctx the parse tree
	 */
	void exitJumpExpression(OolangParser.JumpExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#callableReference}.
	 * @param ctx the parse tree
	 */
	void enterCallableReference(OolangParser.CallableReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#callableReference}.
	 * @param ctx the parse tree
	 */
	void exitCallableReference(OolangParser.CallableReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#assignmentAndOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentAndOperator(OolangParser.AssignmentAndOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#assignmentAndOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentAndOperator(OolangParser.AssignmentAndOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#equalityOperator}.
	 * @param ctx the parse tree
	 */
	void enterEqualityOperator(OolangParser.EqualityOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#equalityOperator}.
	 * @param ctx the parse tree
	 */
	void exitEqualityOperator(OolangParser.EqualityOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(OolangParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(OolangParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#inOperator}.
	 * @param ctx the parse tree
	 */
	void enterInOperator(OolangParser.InOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#inOperator}.
	 * @param ctx the parse tree
	 */
	void exitInOperator(OolangParser.InOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#isOperator}.
	 * @param ctx the parse tree
	 */
	void enterIsOperator(OolangParser.IsOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#isOperator}.
	 * @param ctx the parse tree
	 */
	void exitIsOperator(OolangParser.IsOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveOperator(OolangParser.AdditiveOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveOperator(OolangParser.AdditiveOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#multiplicativeOperator}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeOperator(OolangParser.MultiplicativeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#multiplicativeOperator}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeOperator(OolangParser.MultiplicativeOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#asOperator}.
	 * @param ctx the parse tree
	 */
	void enterAsOperator(OolangParser.AsOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#asOperator}.
	 * @param ctx the parse tree
	 */
	void exitAsOperator(OolangParser.AsOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#prefixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryOperator(OolangParser.PrefixUnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#prefixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryOperator(OolangParser.PrefixUnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#postfixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryOperator(OolangParser.PostfixUnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#postfixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryOperator(OolangParser.PostfixUnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#excl}.
	 * @param ctx the parse tree
	 */
	void enterExcl(OolangParser.ExclContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#excl}.
	 * @param ctx the parse tree
	 */
	void exitExcl(OolangParser.ExclContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessOperator(OolangParser.MemberAccessOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessOperator(OolangParser.MemberAccessOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#safeNav}.
	 * @param ctx the parse tree
	 */
	void enterSafeNav(OolangParser.SafeNavContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#safeNav}.
	 * @param ctx the parse tree
	 */
	void exitSafeNav(OolangParser.SafeNavContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#modifiers}.
	 * @param ctx the parse tree
	 */
	void enterModifiers(OolangParser.ModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#modifiers}.
	 * @param ctx the parse tree
	 */
	void exitModifiers(OolangParser.ModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#parameterModifiers}.
	 * @param ctx the parse tree
	 */
	void enterParameterModifiers(OolangParser.ParameterModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#parameterModifiers}.
	 * @param ctx the parse tree
	 */
	void exitParameterModifiers(OolangParser.ParameterModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(OolangParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(OolangParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#classModifier}.
	 * @param ctx the parse tree
	 */
	void enterClassModifier(OolangParser.ClassModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#classModifier}.
	 * @param ctx the parse tree
	 */
	void exitClassModifier(OolangParser.ClassModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#memberModifier}.
	 * @param ctx the parse tree
	 */
	void enterMemberModifier(OolangParser.MemberModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#memberModifier}.
	 * @param ctx the parse tree
	 */
	void exitMemberModifier(OolangParser.MemberModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#visibilityModifier}.
	 * @param ctx the parse tree
	 */
	void enterVisibilityModifier(OolangParser.VisibilityModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#visibilityModifier}.
	 * @param ctx the parse tree
	 */
	void exitVisibilityModifier(OolangParser.VisibilityModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#varianceModifier}.
	 * @param ctx the parse tree
	 */
	void enterVarianceModifier(OolangParser.VarianceModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#varianceModifier}.
	 * @param ctx the parse tree
	 */
	void exitVarianceModifier(OolangParser.VarianceModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeParameterModifiers}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameterModifiers(OolangParser.TypeParameterModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeParameterModifiers}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameterModifiers(OolangParser.TypeParameterModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#typeParameterModifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameterModifier(OolangParser.TypeParameterModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#typeParameterModifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameterModifier(OolangParser.TypeParameterModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#inheritanceModifier}.
	 * @param ctx the parse tree
	 */
	void enterInheritanceModifier(OolangParser.InheritanceModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#inheritanceModifier}.
	 * @param ctx the parse tree
	 */
	void exitInheritanceModifier(OolangParser.InheritanceModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#annotations}.
	 * @param ctx the parse tree
	 */
	void enterAnnotations(OolangParser.AnnotationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#annotations}.
	 * @param ctx the parse tree
	 */
	void exitAnnotations(OolangParser.AnnotationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(OolangParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(OolangParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#singleAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterSingleAnnotation(OolangParser.SingleAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#singleAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitSingleAnnotation(OolangParser.SingleAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#multiAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterMultiAnnotation(OolangParser.MultiAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#multiAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitMultiAnnotation(OolangParser.MultiAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#annotationUseSiteTarget}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationUseSiteTarget(OolangParser.AnnotationUseSiteTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#annotationUseSiteTarget}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationUseSiteTarget(OolangParser.AnnotationUseSiteTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#unescapedAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterUnescapedAnnotation(OolangParser.UnescapedAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#unescapedAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitUnescapedAnnotation(OolangParser.UnescapedAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleIdentifier(OolangParser.SimpleIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleIdentifier(OolangParser.SimpleIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OolangParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(OolangParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OolangParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(OolangParser.IdentifierContext ctx);
}