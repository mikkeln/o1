package syntaxtree;
import java.util.List;

public class OpExp extends Exp{

  String opname;
  Exp e1;
  Exp e2;
  String op;
  
  public OpExp (String opname, Exp e1, Exp e2, String op) {
    this.opname = opname;
    this.e1 = e1;
    this.e2 = e2;
    this.op = op;
  }

  @Override
  public String printAst(int offset) {
    String out = "(" + opname + " " + op + " ";
    if (e1 != null) out += e1.printAst(offset + 2) + " ";
    if (e2 != null) out += e2.printAst(offset + 2) + " ";
    out += ")";
      
    return out;
  }
}
