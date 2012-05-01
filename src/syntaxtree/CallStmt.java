package syntaxtree;
import java.util.List;

import bytecode.CodeFile;
import bytecode.CodeStruct;
import bytecode.CodeProcedure;
import bytecode.instructions.*;

public class CallStmt extends Stmt {

  String name;
  List<Exp> params;

  public CallStmt(String name, List<Exp> params){
    this.name = name;
    this.params = params;
  }

  public CallExp gexp(){
	  //Returns a new Exp version of this statement.
	  return new CallExp(name, params);
  }
  
  @Override
  public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc){
    System.out.println("CALL - " + name + "()");
    
    //Add params to stack (will the ordering be correct here?)
    if (params != null) {
      for (Exp e : params){
        e.generateCode(file, null, proc);
      }
    }
    
    proc.addInstruction(new CALL(file.procedureNumber(name)));
  }
  

    public String semanticChecker(SymbolTable table, String type){
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
		return "semantic error";

	    for(Exp e : params){
		res = e.semanticChecker(table);
		if (res == null) return "semantic error";


		if (i < sym.nrOfParams()){
		    String parName = sym.getParam(i);

		    SymbolTable param = sym.locateWithinScope(parName);
		    if(param == null){
			if(sym.name.equals("readline") && res.type.equals("void")){
			    return type;
			}else if(sym.name.equals("printint") && res.type.equals("int")){
			    return type;
			}else if(sym.name.equals("printfloat") && 
				 (res.type.equals("float") || res.type.equals("int"))){
			    return type;
			}else if(sym.name.equals("printstr") && res.type.equals("string")){
			    return type;
			}else if(sym.name.equals("printline") && res.type.equals("string")){
			    return type;
			}else{
			    return "semantic error";
			}
       			
		    }else{

			if(!param.type.equals(res.type))
			    return "semantic error";

			if(!(param.byRef == res.byRef))
			    return "semantic error";
		    }

		}else{
		    return "semantic error";
		}
		i++;
	    }
	    return type;
	}
	return "semantic error";
    }




  public String printAst(int offset) {
    String out;
    out = spaces(offset) + "(CALL_STMT (NAME " +  name + ")\n";
    
    if (params != null) {
      for (Exp e : params){
        out += spaces(offset+2) + "(ACTUAL_PARAM " + e.printAst(offset+4) +")";
      }
      out += "\n" + spaces(offset);
    }
    out+= ")\n";
    
    return out;
  }

}
