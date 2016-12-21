/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/***************************/
/* AUTHOR: OREN ISH SHALOM */
/***************************/

/*************/
/* USER CODE */
/*************/
   
import java_cup.runtime.*;  

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/
      
%%
   
/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column
    
/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cupsym CUP_FILESym
%cup
   
/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied letter to letter into the Lexer class code.                */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{   
    /*********************************************************************************/
    /* Create a new java_cup.runtime.Symbol with information about the current token */
    /*********************************************************************************/
    StringBuffer string = new StringBuffer();
    
    private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
    private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}
%}


%eofval{
				System.out.printf("%d: EOF", yyline + 2);
				return symbol(CUP_FILESym.EOF);
		
%eofval}


/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace		= {LineTerminator} | [ \t\f]
INTEGER			= 0 | [1-9][0-9]*
CLASS			= class
EXTENDS			= extends
ID				= [a-z]([a-z]|[0-9]|[A-Z]|_)*
CLASS_ID	 	= [A-Z]([a-z]|[0-9]|[A-Z]|_)*

/* comments */
COMMENT = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent = ( [^*] | \*+ [^/*] )*

//PARTIAL_PRINTABLE_ASCII = [ -!]|[#-\[]|[\]-~]
//ESCAPE_CHARS   	= \t|\n|\\|\"
//QUOTE 			= \"(({PARTIAL_PRINTABLE_ASCII}|{ESCAPE_CHARS})*)$\" 
//QUOTE = \"({PARTIAL_PRINTABLE_ASCII}|{ESCAPE_CHARS})*\"
//QUOTE = \"([^\\\"]|\\.)*\"

%state QUOTE
/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/
   
/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/
   
<YYINITIAL> {


\"                             { string.setLength(0); yybegin(QUOTE); }
   
"+"					
{ 
						return symbol(CUP_FILESym.PLUS);
}
"-"					
{ 
						return symbol(CUP_FILESym.MINUS);
}
"*"					
{ 
						return symbol(CUP_FILESym.TIMES);
}
"/"			
{ 
						return symbol(CUP_FILESym.DIVIDE);
}
"("					
{ 
						return symbol(CUP_FILESym.LPAREN);
}
")"					
{ 
						return symbol(CUP_FILESym.RPAREN);
}
{INTEGER}			
{ 
						return symbol(CUP_FILESym.INTEGER, new Integer(yytext()));
}   
{WhiteSpace}		
{ 
						/* just skip what was found, do nothing */ 
}
{COMMENT}
{
						/* just skip */
}


"="					
{ 
						return symbol(CUP_FILESym.ASSIGN);
}
","					
{ 
						return symbol(CUP_FILESym.COMMA);
}

"."					
{ 
						return symbol(CUP_FILESym.DOT);
}

"=="					
{ 
						return symbol(CUP_FILESym.EQUAL);
}


{CLASS}
{
						return symbol(CUP_FILESym.CLASS);
}

{EXTENDS}
{
						return symbol(CUP_FILESym.EXTENDS);
}


"if"
{
						return symbol(CUP_FILESym.IF);
}		
"int"
{
						return symbol(CUP_FILESym.INT);
}
"&&"
{
						return symbol(CUP_FILESym.LAND);
}
"["
{
						return symbol(CUP_FILESym.LBRACK);
}
"{"
{
						return symbol(CUP_FILESym.LBRACE);
}
"new"
{
						return symbol(CUP_FILESym.NEW);
}
"!"
{
						return symbol(CUP_FILESym.LNEG);
}
"||"
{
						return symbol(CUP_FILESym.LOR);
}
"<"
{
						return symbol(CUP_FILESym.LT);
}
"<="
{
						return symbol(CUP_FILESym.LTE);
}
"%"
{
						return symbol(CUP_FILESym.MOD);
}
"!="
{
						return symbol(CUP_FILESym.NEQUAL);
}
"null"
{
						return symbol(CUP_FILESym.NULL);
}
"]"
{
						return symbol(CUP_FILESym.RBRACK);
}
"}"
{
						return symbol(CUP_FILESym.RBRACE);
}
"return"
{
						return symbol(CUP_FILESym.RETURN);
}
";"
{
						return symbol(CUP_FILESym.SEMICOLON);
}

"string"
{
						return symbol(CUP_FILESym.STRING);
}
"void"
{
						return symbol(CUP_FILESym.VOID);
}
"while"
{
						return symbol(CUP_FILESym.WHILE);
}
{ID}
{
						return symbol(CUP_FILESym.ID, new String(yytext()));
}

{CLASS_ID}
{
						return symbol(CUP_FILESym.CLASS_ID, new String(yytext()));
}

}
">"
{
						return symbol(CUP_FILESym.GT);
}
">="
{
						return symbol(CUP_FILESym.GTE);
}			
<QUOTE> {
      \"                             { yybegin(YYINITIAL); 
                                       return symbol(CUP_FILESym.QUOTE, 
                                       string.toString()); }
      [ -!]                   { string.append( yytext() ); }
      [#-\[]					 { string.append( yytext() ); }
      [\]-~]					 { string.append( yytext() ); }
      \\t                            { string.append('\t'); }
      \\n                            { string.append('\n'); }

      \\r                            { string.append('\r'); }
      \\\"                           { string.append('\"'); }
      \\                             { string.append('\\'); }
    }

.
{
						throw new java.io.IOException();
}
