package compiler;

public class ANTLRCodeGen { 
	public static void main(String[] args) {
		String[] arg0 = { "-visitor",         // 生成语法树visitor遍历器以方便遍历
				"Csub.g4",                    // 设定文法
				"-package", "compiler.ast",   // 设定包的名称
				"-o", "src/compiler/ast" };   // 设定生成的位置
		
		org.antlr.v4.Tool.main(arg0);
	} 
}
