package syntaxtree;
import java.util.List;
import bytecode.CodeFile;
import bytecode.CodeStruct;
import bytecode.CodeProcedure;
import bytecode.instructions.*;


public class CallExp extends Exp {

  String name;
  List<Exp> params;

  public CallExp(String name, List<Exp> params){
    this.name = name;
    this.params = params;
  }

    @Override
    public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc, SymbolTable table){
	System.out.println("CALL - " + name + "()");
	
	//Add params to stack (will the ordering be correct here?)
	if (params != null) {
	    for (Exp e : params){
		e.generateCode(file, null, proc, table);
	    }
	}
	
	int test = file.procedureNumber(name);
	System.out.println("NAME::test:: "+ test);
	
	proc.addInstruction(new CALL(file.procedureNumber(name)));	
	





    }






@Override
    public SymbolTable semanticChecker(SymbolTable table){
	SymbolTable tmp, sym = table;
	Boolean found = false;
	SymbolTable res;


	tmp = table;
	while(tmp != null){
	    if((sym = tmp.locateWithinScope(name)) != null) {
		found = true;
		break;
	    }
	    tmp = tmp.ascend();
	}


	if(found){

	    int i = 0;
	    if(params.size() != sym.nrOfParams())
		return null;


	    for(Exp e : params){
		res = e.semanticChecker(table);
		if (res == null) return null;

		if (i < sym.nrOfParams()){
		    String parName = sym.getParam(i);

		    SymbolTable param = sym.locateWithinScope(parName);

		    if(param == null){
			if(sym.name.equals("readline") && res.type.equals("void")){
			    return sym;
			}else if(sym.name.equals("printint") && res.type.equals("int")){
			    return sym;
			}else if(sym.name.equals("printfloat") && 
				 (res.type.equals("float") || res.type.equals("int"))){
			    return sym;
			}else if(sym.name.equals("printstr") && res.type.equals("string")){
			    return sym;
			}else if(sym.name.equals("printline") && res.type.equals("string")){
			    return sym;
			}else{
			    return null;
			}
       			
		    }else{

			if(!param.type.equals(res.type))
			    return null;

			if(!(param.byRef == res.byRef))
			    return null;
		    }

		}else{
		    return null;
		}
		i++;
	    }

	    return sym;
	}
	return null;
}


  public String printAst(int offset) {
    String out;
    out = "(CALL_STMT (NAME " +  name + ")\n";
    
    if (params != null) {
      for (Exp e : params){
        out += spaces(offset) + "(ACTUAL_PARAM " + e.printAst(offset + 2) +")";
      }
      out += "\n" + spaces(offset);
    }
    out+= ")";
    
    return out;
  }

}
