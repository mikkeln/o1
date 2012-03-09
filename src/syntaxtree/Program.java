package syntaxtree;
import java.util.List;

public class Program {

  List<Decl> decls;

  public Program(List<Decl> decls) {
    this.decls = decls;
  }

  public String printAst(){
    StringBuilder sb = new StringBuilder();
    sb.append("(Program\n");
    for (Decl decl : decls) {
      if (decl == null) { 
        sb.append("  NULL ERROR IN Program\n");
        continue; 
      }
      
      sb.append(decl.printAst(2) + "\n");
    }
    sb.append(")");
    return sb.toString();
  }
}
