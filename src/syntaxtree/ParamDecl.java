package syntaxtree;

public class ParamDecl extends Decl {
  String name;
  String type;
  Boolean byRef;
  
  public ParamDecl (String name, String type, Boolean byRef) {
    this.name = name;
    this.type = type;
    this.byRef = byRef;
  }
  
  @Override
  public String printAst(int offset) {       
    return spaces(offset) + "(PARAM_DECL (TYPE " + type + ") (NAME " + name +"))\n";
  }
  
}
