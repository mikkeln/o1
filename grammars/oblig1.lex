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

InputCharacter = [^\r\n]
LineTerminator = \r|\n|\r\n

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*


WhiteSpace		= {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*
StringLiteral = \" [A-Za-z]* \"
FloatLiteral = [0-9]+ \. [0-9]+
IntLiteral = [0-9]+
Name = [A-Za-z] [A-Za-z0-9]+  //<-- Not working :(


%state STRING

%%
  <YYINITIAL>{
        {WhiteSpace}                    {}
	{Comment}                       {}
        "class"                         { return symbol(sym.CLASS); }
        "proc"                          { return symbol(sym.PROCEDURE); }
        "var"                           { return symbol(sym.VARIABLE); }
        "ref"                           { return symbol(sym.REFERENCE); }
        "null"                          { return symbol(sym.NULL); }
        "true"                          { return symbol(sym.TRUE); }
        "false"                         { return symbol(sym.FALSE); }
        "&&"			                      { return symbol(sym.AND); }
        "||"				                    { return symbol(sym.OR); }
        "not"			                      { return symbol(sym.NOT); }
        "{"			                        { return symbol(sym.LBRACK); }
        "}"				                      { return symbol(sym.RBRACK); }
        "<"			                        { return symbol(sym.LCHEV); }
        ">"				                      { return symbol(sym.RCHEV); }
        "<="			                      { return symbol(sym.LCHEVEQ); }
        ">="				                    { return symbol(sym.RCHEVEQ); }
        "="				                      { return symbol(sym.EQUALS); }
        ":="				                    { return symbol(sym.ASSIGNMENT); }
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
	      ","                             { return symbol(sym.COMMA); }
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
        "ret"                           { return symbol(sym.RET); }
	      "return"                        { return symbol(sym.RETURN); }
        "null"                          { return symbol(sym.NULL); }
        "true"                          { return symbol(sym.TRUE); }
        "false"                         { return symbol(sym.FALSE); }


	      \"                              { string.setLength(0); yybegin(STRING); } 

	      /*Literals*/
	      {FloatLiteral}                  { return symbol(sym.FLOAT_LITERAL, yytext() ); }
	      {IntLiteral}                    { return symbol(sym.INT_LITERAL, yytext()); }
	      //{Name}                          { return symbol(sym.NAME, yytext()); }        
        {Identifier}                    { return symbol(sym.ID,yytext()); }

}


  <STRING>{
  \"                             {  yybegin(YYINITIAL); 
                                    return symbol(sym.STRING_LITERAL, 
				                            string.toString()); }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }

  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }

}

. { throw new Error("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
