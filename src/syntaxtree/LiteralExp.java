package syntaxtree;
import java.util.List;

public class LiteralExp extends Exp {

  String litname;
  String value;
  
  public LiteralExp (String litname, String value) {
    this.litname = litname;
    this.value = value;
  }

  @Override
  public String printAst(int offset) {
    return "(" + litname + "_LITERAL " + value + ")";
  }
}
