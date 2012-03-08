package expression_eval;
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

/* Comment */
{LineTerminator} { }
{WhiteSpace}	{ /* System.out.println("Skipping whitespace"); */ }
{Number}		{ return new Symbol(sym.NUMBER, new Integer(Integer.parseInt(yytext()))); }
";"				{ return new Symbol(sym.SEMI); }
"+" 			{ return new Symbol(sym.PLUS); }
"-"     		{ return new Symbol(sym.MINUS); }
"*" 			{ return new Symbol(sym.TIMES); }
"/" 			{ return new Symbol(sym.DIVIDE); }
"("				{ return new Symbol(sym.LPAREN); }
")"				{ return new Symbol(sym.RPAREN); }
