Windows

1. Download http://antlr.org/download/antlr-4.5.1-complete.jar.

2. Add antlr4-complete.jar to CLASSPATH, either:
  Permanently: Using System Properties dialog > Environment variables > Create or append to CLASSPATH variable
  Temporarily, at command line:
      SET CLASSPATH=.;C:\Javalib\antlr4-complete.jar;%CLASSPATH%
  (此条用本文已经配置好的bat脚本则可忽略)
  

3. Create batch commands for ANTLR Tool, TestRig in dir in PATH
 antlr4.bat: java org.antlr.v4.Tool %*
 grun.bat:   java org.antlr.v4.gui.TestRig %*


4. 执行以下步骤 

   $ antlr4 Expr.g4
   $ javac.bat Expr*.java
   $ grun Expr prog -gui program.txt
     其中 Expr是文法， prog 是起始产生式名称，program.txt是要分析的程序
 