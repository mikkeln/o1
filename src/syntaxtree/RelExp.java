package syntaxtree;
import java.util.List;

public class RelExp extends Exp{

  Exp e1;
  Exp e2;
  String op;
  
  public RelExp (Exp e1, Exp e2, String op) {
    this.e1 = e1;
    this.e2 = e2;
    this.op = op;
  }

  @Override
  public String printAst(int offset) {
    String out = "(REL EXP" + op + " ";
    if (e1 != null) out += e1.printAst(offset + 2) + " ";
    if (e2 != null) out += e2.printAst(offset + 2) + " ";
    out += ")";
      
    return out;
  }
}
