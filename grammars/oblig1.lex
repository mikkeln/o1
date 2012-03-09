package oblig1parser;
import java_cup.runtime.*;
%%

%class Lexer
%unicode
%cup
%line
%column
%public
%{
 StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }

%}
LineTerminator = \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*
%%
<YYINITIAL>{
        {WhiteSpace}                    {}
        "class"                         { return symbol(sym.CLASS); }
        "var"                           { return symbol(sym.VARIABLE); }
        "ref"                           { return symbol(sym.REFERENCE); }
        "null"                          { return symbol(sym.NULL); }
        "true"                          { return symbol(sym.TRUE); }
        "false"                         { return symbol(sym.FALSE); }
        "&&"			                { return symbol(sym.AND); }
        "||"				                    { return symbol(sym.OR); }
        "not"			                      { return symbol(sym.NOT); }
        "{"			                        { return symbol(sym.LBRACK); }
        "}"				                      { return symbol(sym.RBRACK); }
        "<"			                        { return symbol(sym.LCHEV); }
        ">"				                      { return symbol(sym.RCHEV); }
        "<="			                      { return symbol(sym.LCHEVEQ); }
        ">="				                    { return symbol(sym.RCHEVEQ); }
        "="				                      { return symbol(sym.EQUALS); }
        "<>"				                    { return symbol(sym.NEQUAL); }
        "."                             { return symbol(sym.DOT); }
        "+"                             { return symbol(sym.PLUS); }
        "-"                             { return symbol(sym.MINUS); }
        "*"                             { return symbol(sym.TIMES); }
        "/"                             { return symbol(sym.DIVIDE); }
	"**"                            { return symbol(sym.EXPO); }
        "("                             { return symbol(sym.LPAR); }
        ")"                             { return symbol(sym.RPAR); }
        ";"                             { return symbol(sym.SEMI); }
        "new"                           { return symbol(sym.NEW); }
        "float"                         { return symbol(sym.FLOAT); }
        "int"                           { return symbol(sym.INT); }
        "string"                        { return symbol(sym.STRING); }
        "bool"                          { return symbol(sym.BOOL); }
	"if"                            { return symbol(sym.IF); }
	"then"                          { return symbol(sym.THEN); }
	"else"                          { return symbol(sym.ELSE); }
	"while"                         { return symbol(sym.WHILE); }
	"do"                            { return symbol(sym.DO); }

        {Identifier}                    { return symbol(sym.ID,yytext()); }
}

. { throw new Error("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
