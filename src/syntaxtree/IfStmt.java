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

  public String printAst(int offset) {
    String out;
    out = spaces(offset) + "(IF_STMT ( " +  exp.printAst(offset + 2) + " )\n";
    
    if (if_stmt != null) {
      for (Stmt is : if_stmt){
        out += is.printAst(offset + 4);
      }
    }
    
    out += "\n" + spaces(offset) + ")";
    if (else_stmt != null) {
      out += " ELSE (";
      for (Stmt es : else_stmt){
        out += es.printAst(offset + 4);
      }
      out += spaces(offset) + ")\n";
    } else {
      out += "\n";
    }

    
    return out;
  }

}
