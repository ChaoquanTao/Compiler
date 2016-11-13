/** 
 * 本例子使用Soot创建一个类的Jimple三地址表示，然后将其转化为 .class 文件
 *
 * - Create a SootClass <code>HelloWorld</code> extending java.lang.Object.
 * - Create a 'main' method and add it to the class.
 * - Create an empty JimpleBody and add it to the 'main' method.
 * - Add locals and statements to JimpleBody.
 * - Write the result out to a class file.
 */

package soot.example;

import soot.*;
import soot.jimple.*;
import soot.util.*;
 
import java.io.FileOutputStream; 
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class Creatfile
{
    public static void main(String[] args) throws Exception
    {
        SootClass sClass;
        SootMethod method;
        
        // 加载必须的类，System类用于输出，也必须加载 
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");
        //v()总是用来获得Singleton类的一个实例
           
        // 创建一个新的类 'public class HelloWorld'，把我们的代码生成到这个类中
        sClass = new SootClass("HelloWorld", Modifier.PUBLIC);
        
        // 'extends Object'设置继承关系
        sClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
        
        // 加入到总的类表中，从而可以生成.class文件
        Scene.v().addClass(sClass);
           
        // 创建主入口方法  public static void main(String[])，最终生成的代码放在这个入口方法中
        method = new SootMethod("main",
                Arrays.asList(new Type[] {ArrayType.v(RefType.v("java.lang.String"), 1)}),
                VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
        //java.lang.String后面的1表示是一维数组        
        sClass.addMethod(method);
           
        // Create the method body
        {
            //创建方法体，一个method一次只有一个active body
            JimpleBody body = Jimple.v().newBody(method);
            method.setActiveBody(body);
            
            //units是方法内的语句表
            Chain<Unit> units = body.getUnits();
            
            //Local对应的是局部变量
            Local arg, tmpRef;
            
            // 声明局部变量  java.lang.String[] l0
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
            // 下面是关键的生成代码模块，生成我们的程序的代码。这里生成了一个print语句
            // 添加 "tmpRef = java.lang.System.out"，把System.out静态属性域读取到一个局部变量中
            units.add(Jimple.v().newAssignStmt(tmpRef, Jimple.v().newStaticFieldRef(
                    Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())));
            
            // insert "tmpRef.println("Hello world!")"
            {
                SootMethod toCall = Scene.v().getMethod("<java.io.PrintStream: void println(java.lang.String)>");
                units.add(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(), StringConstant.v("Hello world!"))));
            }                        
            
            // ------------------------------------------------------------------------
            // 添加 "return" 语句，Jimple中每个方法必须有return语句
            units.add(Jimple.v().newReturnVoidStmt());                     
        }

        // 在控制台中显示生成的 jimple三地址表示
        Printer.v().printTo(sClass,new java.io.PrintWriter(System.out, true));
        
        // 把jimple三地址表示转化为 .class 文件，这个.class文件可以被执行了
        OutputStream out = new FileOutputStream("./output/HelloWorld.class");
        generateClassFile(sClass, out);					  
    	out.close();
    }
        
	public static void generateClassFile(SootClass cls, OutputStream out) throws Exception{ 
		OutputStream streamOut = new JasminOutputStream(out);
		PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
		JasminClass jasminClass = new soot.jimple.JasminClass(cls);

		jasminClass.print(writerOut);	 
		writerOut.flush();
		
		writerOut.close();		
		streamOut.close();	 
	} 
}
