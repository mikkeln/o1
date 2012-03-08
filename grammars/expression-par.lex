package expression_par;
import java_cup.runtime.*;
%%

%class ExpressionLexer
%unicode
%cup
%public

Number				= [0-9]+
WhiteSpace			= [ \t\f]
LineTerminator		= \r|\n|\r\n

%%

{LineTerminator} { return new Symbol(sym.NEWLINE); }
{WhiteSpace}	{ /* System.out.println("Skipping whitespace"); */ }
{Number}		{ return new Symbol(sym.NUMBER, new Integer(Integer.parseInt(yytext()))); }
"+" 			{ return new Symbol(sym.PLUS); }
"*" 			{ return new Symbol(sym.TIMES); }
