package syntaxtree;
import java.util.List;
import bytecode.CodeFile;

public class Program {

  List<Decl> decls;

  public Program(List<Decl> decls) {
    this.decls = decls;
  }
  
  public void generateCode(CodeFile file) {
    System.out.println("MAKIN' BACON");
    
    for (Decl decl : decls) {
      if (decl == null) { 
        System.out.println("NULL ERROR IN CODE GEN!\n");
      }
      
      decl.generateCode(file);
    }
    
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
