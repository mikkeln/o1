package syntaxtree;
import bytecode.*;
/*import bytecode.CodeFile;
import bytecode.CodeStruct;
import byteCode.CodeProcedure;*/

public class VarDecl extends Decl {
  String name;
  String type;
  
  public VarDecl (String name, String type) {
    this.name = name;
    this.type = type;
  }
    /*
    @Override 
    public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc){
	CodeType ct;

    	if(type.equals("float")) 
	    ct = new FloatType();
	else if(type.equals("int"))
	    ct = new IntType();
	else if(type.equals("boolean"))
	    ct = new BoolType();
	else if(type.equals("string"))
	    ct = new StringType();
	else
	    ct = new CodeType(); //correct? what if typeof some object?


	if(struct != null){//if program class/struct
	    struct.addVariable(name, ct);
	}else{//if program procedure
	    prod.addVariable(name, ct);
	}
    }
    */




    @Override
    public String semanticChecker(SymbolTable table){

	//go to top symboltable level
	SymbolTable top = table;
	SymbolTable test;
	while(top.ascend() != null){
	    top = top.ascend();
	}
	test = null;
 
	//check if variable exist
	if(top.locate(name) != null || (test = table.locate(name)) != null){
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
