package syntaxtree;

public class VarDecl extends Decl {
  String name;
  String type;
  
  public VarDecl (String name, String type) {
    this.name = name;
    this.type = type;
  }
    
    @Override
    public String semanticChecker(SymbolTable table){

	//go to top symboltable level
	SymbolTable top = table;
	while(top.ascend() != null){
	    top = top.ascend();
	}

	//check if variable exist
	if(top.locate(name) != null || table.locate(name) != null){
	    return "semantic error"; //syntaxError
	}

	//create new entry
	table.newEntry(type, name);

	return type; //yeah baby!
    }




  @Override
  public String printAst(int offset) {       
    return spaces(offset) + "(VAR_DECL (TYPE " + type + ") (NAME " + name +"))\n";
  }
}
