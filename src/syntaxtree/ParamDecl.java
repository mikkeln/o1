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
	public String semanticChecker(SymbolTable table){

	//System.out.println("PARAMDECL");

	SymbolTable top = table;

	while(top.parent != null)
	    top = top.ascend();

	if(top.locate(name) != null)
	   return "semantic error";


	//create new entry
       	SymbolTable newtable = table.newEntry(type, name);
	newtable.byRef = byRef;

	//	System.out.println("new added name and type: " + newtable.name + " " + newtable.type);

	return newtable.name;
    }
  
  @Override
  public String printAst(int offset) {       
    return spaces(offset) + "(PARAM_DECL (TYPE " + type + ") (NAME " + name +"))\n";
  }
  
}

