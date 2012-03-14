package syntaxtree;
import java.util.List;

public class WhileStmt extends Stmt {

  Exp exp;
  List<Stmt> while_stmt;

  public WhileStmt(Exp exp, List<Stmt> while_stmt){
    this.exp = exp;
    this.while_stmt = while_stmt;
  }

  public String printAst(int offset) {
    String out;
    out = spaces(offset) + "(WHILE_STMT ( " +  exp.printAst(offset + 2) + " )\n";
    
    if (while_stmt != null) {
      for (Stmt is : while_stmt){
        out += is.printAst(offset + 4);
      }
    }    
    out += "\n" + spaces(offset) + ")";

    return out;
  }

}
