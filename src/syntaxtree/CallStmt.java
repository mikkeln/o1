package syntaxtree;
import java.util.List;

public class CallStmt extends Stmt {

  String name;
  List<Exp> params;

  public CallStmt(String name, List<Exp> params){
    this.name = name;
    this.params = params;
  }

  public CallExp gexp(){
    //Returns a new Exp version of this statement.
    return new CallExp(name, params);
  }

  public String printAst(int offset) {
    String out;
    out = "(CALL_STMT (NAME " +  name + ")\n";
    
    if (params != null) {
      for (Exp e : params){
        out += spaces(offset+2) + "(ACTUAL_PARAM " + e.printAst(offset+4) +")";
      }
      out += "\n" + spaces(offset);
    }
    out+= ")\n" + spaces(offset);
    
    return out;
  }

}
