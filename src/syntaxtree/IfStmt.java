package syntaxtree;
import java.util.List;

public class IfStmt extends Stmt {

  Exp exp;
  List<Stmt> if_stmt;
  List<Stmt> else_stmt;

  public IfStmt(Exp exp, List<Stmt> if_stmt, List<Stmt> else_stmt){
    this.exp = exp;
    this.if_stmt = if_stmt;
    this.else_stmt = else_stmt;
  }

    public String semanticChecker(SymbolTable table, String type){
	SymbolTable eres;
	String one, two;
	//String eres;
	String test;

	if(exp == null)
	    return "syntax error";

	eres = exp.semanticChecker(table);
	//Check that bool
	if(eres.type != "bool")
	    return "semantic error";

	if(if_stmt != null){
	    for(Stmt s : if_stmt){
		if(s == null) return "syntax error";
		one = s.semanticChecker(table, type);
		if(one == "semantic error") return one;
	
	    } 
	}

	if(else_stmt != null){
	    for(Stmt s : else_stmt){
		if(s == null) return "syntax error";
		two = s.semanticChecker(table, type);
		if(two == "semantic error") return two;
	    } 
	}

	return "no error";
    }





  public String printAst(int offset) {
    String out;
    out = spaces(offset) + "(IF_STMT " +  exp.printAst(offset + 2) + " \n";
    
    if (if_stmt != null) {
      out += spaces(offset+2) + "(\n";
      for (Stmt is : if_stmt){
        out += is.printAst(offset + 4);
      }
      out += spaces(offset+2) + ")\n";
    }
    
    out += "\n" + spaces(offset) + ")";
    if (else_stmt != null) {
      out += " ELSE (\n";
      for (Stmt es : else_stmt){
        out += es.printAst(offset + 4);
      }
      out += spaces(offset) + ")\n" + spaces(offset);
    } else {
      out += "\n";
    }

    
    return out;
  }

}
