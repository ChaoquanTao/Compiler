@echo on
 
SET CLASSPATH=.;%~dp0antlr-4.5.1-complete.jar;%CLASSPATH%
java org.antlr.v4.Tool %*

@echo off
