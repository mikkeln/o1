package syntaxtree;
import java.util.List;

public class CallExp extends Exp {

  String name;
  List<Exp> params;

  public CallExp(String name, List<Exp> params){
    this.name = name;
    this.params = params;
  }

  public String printAst(int offset) {
    String out;
    out = "(CALL_STMT (NAME " +  name + ")\n";
    
    if (params != null) {
      for (Exp e : params){
        out += spaces(offset) + "(ACTUAL_PARAM " + e.printAst(offset + 2) +")";
      }
      out += "\n" + spaces(offset);
    }
    out+= ")";
    
    return out;
  }

}
