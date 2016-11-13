package compiler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import compiler.ast.CsubBaseVisitor;
import compiler.ast.CsubLexer;
import compiler.ast.CsubParser;
import compiler.ast.CsubParser.Assignment_statementContext;
import compiler.ast.CsubParser.BlockItemContext;
import compiler.ast.CsubParser.ExpressionContext;
import compiler.ast.CsubParser.IntegerContext;
import compiler.ast.CsubParser.Variable_nameContext;
import compiler.ast.CsubParser.Variable_specifierContext;
import compiler.ast.CsubParser.Variable_typeContext;
import soot.ArrayType;
import soot.CharType;
import soot.IntType;
import soot.Local;
import soot.Modifier;
import soot.Printer;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.VoidType;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.util.Chain;
import soot.util.JasminOutputStream;

// 这里ExprBaseVisitor换成我们自己的Visitor
public class ASTVisitor extends CsubBaseVisitor<Void> {

	public static void main(String[] args) throws Exception {
		String testProgram = "program.txt";
		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(testProgram));

		// 这里请把ExprLexer和ExprParser换成我们自己文法生成的lexer和parser类
		CsubLexer lexer = new CsubLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CsubParser parser = new CsubParser(tokens);

		// 这里调用的方法换成我们自己文法的起始符号
		ParseTree tree = parser.prog();

		SootClass sClass;
		SootMethod method;

		// 加载必须的类，System类用于输出，也必须加载
		Scene.v().loadClassAndSupport("java.lang.Object");
		Scene.v().loadClassAndSupport("java.lang.System");
		// v()总是用来获得Singleton类的一个实例

		// 创建一个新的类 'public class HelloWorld'，把我们的代码生成到这个类中
		sClass = new SootClass("HelloWorld", Modifier.PUBLIC);

		// 'extends Object'设置继承关系
		sClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));

		// 加入到总的类表中，从而可以生成.class文件
		Scene.v().addClass(sClass);

		// 创建主入口方法 public static void main(String[])，最终生成的代码放在这个入口方法中
		method = new SootMethod("main", Arrays.asList(new Type[] { ArrayType.v(RefType.v("java.lang.String"), 1) }),
				VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
		// java.lang.String后面的1表示是一维数组
		sClass.addMethod(method);
		/****************************************************************************/
		// Create the method body
		// {
		// 创建方法体，一个method一次只有一个active body
		JimpleBody body = Jimple.v().newBody(method);
		method.setActiveBody(body);

		// units是方法内的语句表
		Chain<Unit> units = body.getUnits();

		// Local对应的是局部变量
		Local arg, tmpRef;

		// 声明局部变量 java.lang.String[] l0
		// l0 变量存放的是main函数的命令行参数
		arg = Jimple.v().newLocal("l0", ArrayType.v(RefType.v("java.lang.String"), 1));
		body.getLocals().add(arg);

		// 创建一个存放 System.out值的临时局部变量, java.io.printStream tmpRef
		tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("java.io.PrintStream"));
		body.getLocals().add(tmpRef);

		// 创建 IdentityStmt语句，该语句用于读取方法参数，将实参取值读入到形参中
		// add "l0 = @parameter0" 添加语句，参数是从参数栈上按顺序获取的
		units.add(Jimple.v().newIdentityStmt(arg,
				Jimple.v().newParameterRef(ArrayType.v(RefType.v("java.lang.String"), 1), 0)));

		// ------------------------------------------------------------------------
		CompileResult result = compile(tree, body);
		// for(int i=0; i<tree.getChildCount();i++){
		// body.getUnits().add(tree.getChild(i).);
		// }
		for (Unit u : result.code) {
			body.getUnits().add(u);
		}
		// ------------------------------------------------------------------------
		// 添加 "return" 语句，Jimple中每个方法必须有return语句
		units.add(Jimple.v().newReturnVoidStmt());
		// }

		// 在控制台中显示生成的 jimple三地址表示
		Printer.v().printTo(sClass, new java.io.PrintWriter(System.out, true));

		// 把jimple三地址表示转化为 .class 文件，这个.class文件可以被执行了
		OutputStream out = new FileOutputStream("./output/HelloWorld.class");
		generateClassFile(sClass, out);
		out.close();

	}

	// static int t = 1;

	protected static CompileResult compile(ParseTree tree, JimpleBody body) {
		// CompileResult result= null;
		String text = tree.getText();
		// System.out.println("tree:"+tree.getText());
		List<CompileResult> childCompileResult = new ArrayList<CompileResult>();
		int childCount = tree.getChildCount();

		// System.out.println("第" + t + "次 childcount:" + childCount);
		// t++;
		Set<Unit> lastBrotherBackPatch = null;

		for (int i = 0; i < childCount; i++) {
			ParseTree child = tree.getChild(i);
			// System.out.println("child:" + child.getText());
			CompileResult childResult = compile(child, body);
			childCompileResult.add(childResult);
			// childCompileResult.
		}
		CompileResult result = new CompileResult();

		if (tree instanceof Assignment_statementContext) {
			// // code place huitian
			result.place = childCompileResult.get(2).place;
	//		System.out.println("place:" + result.place);
		
			Local local = Jimple.v().newLocal(tree.getChild(0).getText(), CharType.v());
			CompileResult r = new CompileResult();
			
			r.place = local;
			
		//	System.out.println(r.place);
			Unit u = Jimple.v().newAssignStmt(local, result.place);
		//Local local = Jimple.v().newLocal(tree.getChild(0).getText(), Type.v());
			body.getUnits().add(u);
		//	body.getLocals().add(local);
			System.out.println("ddd" + u);
			result.code.addAll(childCompileResult.get(2).code);
			result.code.add(u);
		}
		// System.out.println(tree instanceof Variable_specifierContext);
		if (tree instanceof Variable_specifierContext) {
			Local local = Jimple.v().newLocal(tree.getChild(1).getText(), IntType.v());

			body.getLocals().add(local);
			System.out.println(local);
		}

		if (tree instanceof ExpressionContext) {

			if (tree.getChildCount() == 1) {
				Local local = Jimple.v().newLocal(tree.getChild(0).getText(), IntType.v());
				result.place = local;
				System.out.println(result.place);
				result.code = childCompileResult.get(0).code;
			}
			
			if(tree.getChildCount() ==3){
				CompileResult r1 = new CompileResult(); // 等号左边符号
				CompileResult r2 = new CompileResult(); // 等号右边符号
				r1.place = childCompileResult.get(0).place;
				r2.place = childCompileResult.get(2).place;
				
				r1.code = childCompileResult.get(0).code;
				r2.code = childCompileResult.get(2).code;
				Unit assign1 = Jimple.v().newAssignStmt(r1.place, r2.place);
//				result.code.addAll(r1.code);
//				result.code.addAll(r2.code);
				result.code.add(assign1);
				if(tree.getChild(1).getText().equals("+")){
					result.place = Jimple.v().newAddExpr(r1.place, r2.place) ;
				}
				if(tree.getChild(1).getText().equals("-")){
					result.place = Jimple.v().newSubExpr(r1.place, r2.place) ;
				}
				if(tree.getChild(1).getText().equals("*")){
					result.place = Jimple.v().newMulExpr(r1.place, r2.place) ;
				}
				if(tree.getChild(1).getText().equals("/")){
					result.place = Jimple.v().newDivExpr(r1.place, r2.place) ;
				}
				

			}
//			if(tree.getChildCount()>3){
////				for(int i=0; i<tree.getChildCount(); i++){
////					
////				}
//				CompileResult temp = new CompileResult();
//				CompileResult t1 = new CompileResult();
//				CompileResult t2 = new CompileResult();
//				t1.place = childCompileResult.get(2).place;
//				t2.place = childCompileResult.get(4).place;
//				
//				temp.place = Jimple.v().newMulExpr(t1.place, t2.place) ;
//				result.place = Jimple.v().newAddExpr(temp.place, childCompileResult.get(0).place) ;
//				
//				Unit assign1 = Jimple.v().newAssignStmt(temp1.place, r2.place);
////				result.code.addAll(r1.code);
////				result.code.addAll(r2.code);
//				result.code.add(assign1);
//				
//			}
		}
//		if (tree instanceof IntegerContext) {
//			// result.place = childCompileResult.get(0).place;
//			Local local = Jimple.v().newLocal(tree.getChild(0).getText(), IntType.v());
//			result.place = local;
//			body.getLocals().add(local);
//		}
		// if (tree instanceof Variable_typeContext) {
		// result.place = childCompileResult.get(0).place;
		// result.code = childCompileResult.get(0).code;
		// }
		//
		// if (tree instanceof Variable_nameContext) {
		// result.code = childCompileResult.get(0).code;
		// result.place = childCompileResult.get(0).place;
		//
		//
		// }

		return result;

	}

	public static void generateClassFile(SootClass cls, OutputStream out) throws Exception {
		OutputStream streamOut = new JasminOutputStream(out);
		PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
		JasminClass jasminClass = new soot.jimple.JasminClass(cls);

		jasminClass.print(writerOut);
		writerOut.flush();

		writerOut.close();
		streamOut.close();
	}

	// 处理每种遇到的符号，参见父类和接口中的方法声明
	@Override
	public Void visitProg(CsubParser.ProgContext ctx) {
		System.out.println("Prog:" + ctx.getText());
		return super.visitProg(ctx);
	}

	@Override
	public Void visitAssignment_statement(CsubParser.Assignment_statementContext ctx) {
		System.out.println("Assignment_statement:" + ctx.getText());
		return super.visitAssignment_statement(ctx);
	}

	@Override
	public Void visitVariable_specifier(CsubParser.Variable_specifierContext ctx) {
		System.out.println("Variable_specifier:" + ctx.getText());
		return super.visitVariable_specifier(ctx);
	}

	@Override
	public Void visitVariable_name(CsubParser.Variable_nameContext ctx) {
		System.out.println("Variable_name:" + ctx.getText());
		return super.visitVariable_name(ctx);
	}

	@Override
	public Void visitVariable_type(CsubParser.Variable_typeContext ctx) {
		System.out.println("Variable_type:" + ctx.getText());
		return super.visitVariable_type(ctx);
	}

}