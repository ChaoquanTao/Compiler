Windows

1. Download http://antlr.org/download/antlr-4.5.1-complete.jar.

2. Add antlr4-complete.jar to CLASSPATH, either:
  Permanently: Using System Properties dialog > Environment variables > Create or append to CLASSPATH variable
  Temporarily, at command line:
      SET CLASSPATH=.;C:\Javalib\antlr4-complete.jar;%CLASSPATH%
  (�����ñ����Ѿ����úõ�bat�ű���ɺ���)
  

3. Create batch commands for ANTLR Tool, TestRig in dir in PATH
 antlr4.bat: java org.antlr.v4.Tool %*
 grun.bat:   java org.antlr.v4.gui.TestRig %*


4. ִ�����²��� 

   $ antlr4 Expr.g4
   $ javac.bat Expr*.java
   $ grun Expr prog -gui program.txt
     ���� Expr���ķ��� prog ����ʼ����ʽ���ƣ�program.txt��Ҫ�����ĳ���
 