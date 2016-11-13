// Generated from Csub.g4 by ANTLR 4.5.1
package compiler.ast;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CsubParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CsubVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CsubParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(CsubParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(CsubParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#funName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunName(CsubParser.FunNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(CsubParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#variable_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_specifier(CsubParser.Variable_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#variable_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_type(CsubParser.Variable_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#variable_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_name(CsubParser.Variable_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CsubParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#assignment_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_statement(CsubParser.Assignment_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#condition_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition_statement(CsubParser.Condition_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#compound_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_statement(CsubParser.Compound_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#iteration_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteration_statement(CsubParser.Iteration_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#blockItemList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItemList(CsubParser.BlockItemListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#blockItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItem(CsubParser.BlockItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CsubParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#bool_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_expression(CsubParser.Bool_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#relational_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_operator(CsubParser.Relational_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CsubParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(CsubParser.IntegerContext ctx);
}