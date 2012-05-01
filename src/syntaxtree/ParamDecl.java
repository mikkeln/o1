package syntaxtree;
import bytecode.*;
import bytecode.type.*;
import bytecode.instructions.*;


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
    public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc){
	
	if(type.equals("float"))
	    proc.addParameter(name, FloatType.TYPE);
	else if(type.equals("int"))
	    proc.addParameter(name, IntType.TYPE);
	else if(type.equals("bool"))
	    proc.addParameter(name, BoolType.TYPE);
	else if(type.equals("string"))
	    proc.addParameter(name, StringType.TYPE);
	else
	    proc.addParameter(name, new RefType(proc.structNumber(type))); //or file.structNumber?


    }






    @Override
	public String semanticChecker(SymbolTable table){

	SymbolTable top = table;

	while(top.parent != null)
	    top = top.ascend();

	if(top.locate(name) != null){
	    return "semantic error";
	}

	//create new entry
       	SymbolTable newtable = table.newEntry(type, name);
	newtable.byRef = byRef;

	return newtable.name;
    }
  
  @Override
  public String printAst(int offset) {       
    return spaces(offset) + "(PARAM_DECL (TYPE " + type + ") (NAME " + name +"))\n";
  }
  
}

