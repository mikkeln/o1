package syntaxtree;

public class VarDecl extends Decl {
  String name;
  String type;
  
  public VarDecl (String name, String type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public String printAst(int offset) {       
    return spaces(offset) + "(VAR_DECL (TYPE " + type + ") (NAME " + name +"))\n";
  }
}
