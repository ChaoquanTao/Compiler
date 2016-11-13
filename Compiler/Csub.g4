grammar Csub;
prog
	: function ;
function
	: funName body ;
funName 
	: 'main' ;
body 
    :  '{' variable_specifier statement '}';
	
variable_specifier
	: ( variable_type variable_name ';')+;

variable_type
	: 'int' ;
variable_name
	: Indentifier
	;

statement
	: (
	  assignment_statement
	| condition_statement
	| compound_statement
	| iteration_statement
	  )+
	;
assignment_statement
	: Indentifier '=' expression ';'
	;
condition_statement
	: 'if' '(' bool_expression ')' statement 'else' statement
	;
compound_statement
	: '{' blockItemList? '}'
    ;
iteration_statement
	: 'for' '(' assignment_statement?  bool_expression? ';' expression? ')' statement
	;

blockItemList
    :   blockItem
    |   blockItemList blockItem
    ;

blockItem
    :   variable_specifier
    |   statement
    ;
	
expression
	: expression ('+'|'-') expression
	| expression ('*'|'/') expression
	| expression '++'
	| '(' expression ')'
	| integer
	| Indentifier
	;

bool_expression
	: expression relational_operator expression
	| expression
	;
relational_operator
	: '='
	| '<'
	| '>'
	| '<='
	| '>='
	;
	

integer
	: Number
	| integer Number
	;
	
	
Indentifier
    :  Nondigit
        (   Nondigit
        |   Number
			)*
    ;
 
fragment
Nondigit
    :   [a-zA-Z_]
    ;
	

Number
	: [0-9]
	;
	
Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;
Whitespace
    :   [ \t]+
        -> skip
    ;