package soot.example;

import soot.*;
import soot.jimple.*;
import soot.util.*;
import java.util.*;

/** 
          代码插桩示例，用来统计程序（Java字节码）中goto跳转语句的执行次数

    Instruments the given class to print out the number of Jimple goto 
    statements executed.

    To enable this class, enable the given PackAdjuster by compiling it 
    separately, into the soot package.
 */

//BodyTransformer是一个用以回调的类，基于Soot的分析大多从继承它开始
public class GotoInstrumenter extends BodyTransformer
{
	//singleton模式，v()总是用来获得类的唯一实例
    private static GotoInstrumenter instance = new GotoInstrumenter();
    private GotoInstrumenter() {}

    public static GotoInstrumenter v() { return instance; }

    
    private boolean addedFieldToMainClassAndLoadedPrintStream = false;
    
    private SootClass javaIoPrintStream;

    private Local addTmpRef(Body body)
    {
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("java.io.PrintStream"));
        body.getLocals().add(tmpRef);
        return tmpRef;
    }
     
    private Local addTmpLong(Body body)
    {
        Local tmpLong = Jimple.v().newLocal("tmpLong", LongType.v()); 
        body.getLocals().add(tmpLong);
        return tmpLong;
    }

    private void addStmtsToBefore(Chain units, Stmt s, SootField gotoCounter, Local tmpRef, Local tmpLong)
    {
        // insert "tmpRef = java.lang.System.out;" 
        units.insertBefore(Jimple.v().newAssignStmt( 
                      tmpRef, Jimple.v().newStaticFieldRef( 
                      Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())), s);

        // insert "tmpLong = gotoCounter;" 
        units.insertBefore(Jimple.v().newAssignStmt(tmpLong, 
                      Jimple.v().newStaticFieldRef(gotoCounter.makeRef())), s);
            
        // insert "tmpRef.println(tmpLong);" 
        SootMethod toCall = javaIoPrintStream.getMethod("void println(long)");                    
        units.insertBefore(Jimple.v().newInvokeStmt(
                      Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(), tmpLong)), s);
    }

   /**
    * 用于实现Transform的核心方法，每个从BodyTransformer的类都应该重载该方法
    */
    protected void internalTransform(Body body, String phaseName, Map options)
    {
    	//获得代插装的类
        //SootClass sClass = body.getMethod().getDeclaringClass();
        //goto语句计数器
        SootField gotoCounter = null;
        boolean addedLocals = false;
        //几个待插入的局部变量
        Local tmpRef = null, tmpLong = null;
        //获得原函数体
        Chain units = body.getUnits();
        
        // Add code at the end of the main method to print out the 
        // gotoCounter (this only works in simple cases, because you may have multiple returns or System.exit()'s )
        synchronized(this)
        {
        	//判断分析器所认为的main class中是否有main方法，否则不能直接收集数据
            if (!Scene.v().getMainClass().
                    declaresMethod("void main(java.lang.String[])"))
                throw new RuntimeException("couldn't find main() in mainClass");

            //如果已生成gotoCount则直接添加，否则需要新建
            if (addedFieldToMainClassAndLoadedPrintStream)
                gotoCounter = Scene.v().getMainClass().getFieldByName("gotoCount");
            else
            {
                //生成并添加类成员gotoCounter
                gotoCounter = new SootField("gotoCount", LongType.v(), 
                                                Modifier.STATIC);
                Scene.v().getMainClass().addField(gotoCounter);

                // Just in case, resolve the PrintStream SootClass.
                Scene.v().loadClassAndSupport("java.lang.Object");
                
                //这里load这个java.io.PrintStream不对，只要load java.lang.Object即可
                //可能是新的版本中在load Object的时候顺带也把PrintStream递归的load了
                
                //Scene.v().loadClassAndSupport("java.io.PrintStream");
                javaIoPrintStream = Scene.v().getSootClass("java.io.PrintStream");

                addedFieldToMainClassAndLoadedPrintStream = true;
            }
        }
            
        // Add code to increase goto counter each time a goto is encountered
        {
            boolean isMainMethod = body.getMethod().getSubSignature().equals("void main(java.lang.String[])");

            Local tmpLocal = Jimple.v().newLocal("tmp", LongType.v());
            body.getLocals().add(tmpLocal);
                
            Iterator stmtIt = units.snapshotIterator();
            
            while(stmtIt.hasNext())
            {
                Stmt s = (Stmt) stmtIt.next();

                if(s instanceof GotoStmt)
                {
                    AssignStmt toAdd1 = Jimple.v().newAssignStmt(tmpLocal, 
                                 Jimple.v().newStaticFieldRef(gotoCounter.makeRef()));
                    AssignStmt toAdd2 = Jimple.v().newAssignStmt(tmpLocal,
                                 Jimple.v().newAddExpr(tmpLocal, LongConstant.v(1L)));
                    AssignStmt toAdd3 = Jimple.v().newAssignStmt(Jimple.v().newStaticFieldRef(gotoCounter.makeRef()), 
                                                                 tmpLocal);

                    // insert "tmpLocal = gotoCounter;"
                    units.insertBefore(toAdd1, s);
                        
                    // insert "tmpLocal = tmpLocal + 1L;" 
                    units.insertBefore(toAdd2, s);

                    // insert "gotoCounter = tmpLocal;" 
                    units.insertBefore(toAdd3, s);
                }
                else if (s instanceof InvokeStmt)
                {
                    InvokeExpr iexpr = ((InvokeStmt)s).getInvokeExpr();
                    if (iexpr instanceof StaticInvokeExpr)
                    {
                        SootMethod target = ((StaticInvokeExpr)iexpr).getMethod();
                        
                        if (target.getSignature().equals("<java.lang.System: void exit(int)>"))
                        {
                            if (!addedLocals)
                            {
                                tmpRef = addTmpRef(body); tmpLong = addTmpLong(body);
                                addedLocals = true;
                            }
                            addStmtsToBefore(units, s, gotoCounter, tmpRef, tmpLong);
                        }
                    }
                }
                else if (isMainMethod && (s instanceof ReturnStmt || s instanceof ReturnVoidStmt))
                {
                    if (!addedLocals)
                    {
                        tmpRef = addTmpRef(body); tmpLong = addTmpLong(body);
                        addedLocals = true;
                    }
                    addStmtsToBefore(units, s, gotoCounter, tmpRef, tmpLong);
                }
            }
        }
    }
    
    public static void main(String[] args){
    	/*String args[]={"--soot-classpath",
	               ".;E:\\j2sdk1.4.1\\lib;E:\\j2sdk1.4.1\\jre\\lib\\rt.jar;E:\\MyProject\\Harpy\\testfile",
	               "Hello"};*/
    	String newArgs[]={"app","-f","class",/*"jimple"*/"--soot-classpath",
	               ".;E:\\j2sdk1.4.1\\lib;E:\\j2sdk1.4.1\\jre\\lib\\rt.jar;E:\\MyProject\\Harpy\\testfile",
	               "SootTest"};
    	PackManager.v().getPack("jtp").add(new Transform("jtp.instrumenter", GotoInstrumenter.v()));
    	soot.Main.main(newArgs);
    }
    
}















