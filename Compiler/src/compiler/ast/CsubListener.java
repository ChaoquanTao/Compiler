// Generated from Csub.g4 by ANTLR 4.5.1
package compiler.ast;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CsubParser}.
 */
public interface CsubListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CsubParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CsubParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CsubParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(CsubParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(CsubParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#funName}.
	 * @param ctx the parse tree
	 */
	void enterFunName(CsubParser.FunNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#funName}.
	 * @param ctx the parse tree
	 */
	void exitFunName(CsubParser.FunNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(CsubParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(CsubParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#variable_specifier}.
	 * @param ctx the parse tree
	 */
	void enterVariable_specifier(CsubParser.Variable_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#variable_specifier}.
	 * @param ctx the parse tree
	 */
	void exitVariable_specifier(CsubParser.Variable_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#variable_type}.
	 * @param ctx the parse tree
	 */
	void enterVariable_type(CsubParser.Variable_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#variable_type}.
	 * @param ctx the parse tree
	 */
	void exitVariable_type(CsubParser.Variable_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#variable_name}.
	 * @param ctx the parse tree
	 */
	void enterVariable_name(CsubParser.Variable_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#variable_name}.
	 * @param ctx the parse tree
	 */
	void exitVariable_name(CsubParser.Variable_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CsubParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CsubParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#assignment_statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_statement(CsubParser.Assignment_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#assignment_statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_statement(CsubParser.Assignment_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#condition_statement}.
	 * @param ctx the parse tree
	 */
	void enterCondition_statement(CsubParser.Condition_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#condition_statement}.
	 * @param ctx the parse tree
	 */
	void exitCondition_statement(CsubParser.Condition_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#compound_statement}.
	 * @param ctx the parse tree
	 */
	void enterCompound_statement(CsubParser.Compound_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#compound_statement}.
	 * @param ctx the parse tree
	 */
	void exitCompound_statement(CsubParser.Compound_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void enterIteration_statement(CsubParser.Iteration_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void exitIteration_statement(CsubParser.Iteration_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void enterBlockItemList(CsubParser.BlockItemListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void exitBlockItemList(CsubParser.BlockItemListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void enterBlockItem(CsubParser.BlockItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void exitBlockItem(CsubParser.BlockItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CsubParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CsubParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#bool_expression}.
	 * @param ctx the parse tree
	 */
	void enterBool_expression(CsubParser.Bool_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#bool_expression}.
	 * @param ctx the parse tree
	 */
	void exitBool_expression(CsubParser.Bool_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#relational_operator}.
	 * @param ctx the parse tree
	 */
	void enterRelational_operator(CsubParser.Relational_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#relational_operator}.
	 * @param ctx the parse tree
	 */
	void exitRelational_operator(CsubParser.Relational_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CsubParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(CsubParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CsubParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(CsubParser.IntegerContext ctx);
}