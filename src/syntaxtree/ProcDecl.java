package syntaxtree;
import java.util.List;

public class ProcDecl extends Decl{

  String name;
  String type;
  List<Decl> parlist;
  List<Decl> decllist;
  
  public ProcDecl (String name, String type, List<Decl> parlist, 
                    List<Decl> decllist) {
    this.name = name;
    this.type = (type == null)? "void" : type; // No type implies void.
    this.parlist = parlist;
    this.decllist = decllist;
  }


  @Override
  public String printAst(int offset) {
    
    String out = spaces(offset) + "(PROC_DECL (NAME " + name + ")(TYPE " + type + ")\n";
    if (parlist != null) {
      for (Decl p: parlist){
        if (p == null) {
          out += "NULL ERROR IN ProcDecl\n";
          continue;
        }
        out+= p.printAst(offset + 2);
      }
    }
    
    if (decllist != null) {
      for (Decl d: decllist){
        if (d == null) {
          out += "NULL ERROR IN ProcDecl\n";
          continue;
        }
        out+= d.printAst(offset + 2);
      }
    }
    
    
    out += spaces(offset) + ")\n";
      
    return out;
  }
}