package syntaxtree;
import java.util.List;

public class NameExp extends Exp{

  String name;
  Exp e1 = null;
  
  public NameExp(String name) {
    this.name = name;
  }
  
  public NameExp(String name, Exp e1) {
    this.name = name;
    this.e1 = e1;
  }

  @Override
  public String printAst(int offset) {
    String out = "";
    
    if (e1 == null){
      out = "(NAME " + name + ")";
    } else {
      out = "( . (NAME " + name + ") "+ e1.printAst(offset) +")"; 
    }
      
    return out;
  }
}
