package syntaxtree;
import java.util.List;
import bytecode.CodeFile;

public class ClassDecl extends Decl{

  String name;
  List<Decl> vlist;
  
  public ClassDecl (String name, List<Decl> vlist) {
    this.name = name;
    this.vlist = vlist;
  }

  public void generateCode(CodeFile file) {
    System.out.println("MAKIN' SOME CLASS CODE");
    
    CodeStruct struct = new CodeStruct(this.name);
    
    if (vlist != null) {
      for (Decl v: vlist){
        if (v == null) {
          out += "NULL ERROR IN ClassDecl\n";
          continue;
        }
        // Add decl to to struct!
      }
    }
  }

  @Override
  public String printAst(int offset) {
    
    String out = spaces(offset) + "(CLASS_DECL (NAME " + name + ")\n";
    if (vlist != null) {
      for (Decl v: vlist){
        if (v == null) {
          out += "NULL ERROR IN ClassDecl\n";
          continue;
        }
        out+= v.printAst(offset + 2);
      }
    }
    out += spaces(offset) + ")\n";
      
    return out;
  }
}
