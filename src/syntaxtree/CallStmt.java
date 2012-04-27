package syntaxtree;
import java.util.List;

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

    public String semanticChecker(SymbolTable table, String type){
	SymbolTable tmp, sym = table;
	Boolean found = false;
	SymbolTable res;

	//System.out.println("callstmt with name: " + name);
	

	tmp = table;
	while(tmp != null){
	    //System.out.println("while");
	    if((sym = tmp.locateWithinScope(name)) != null) {
		//System.out.println("WTF");
		found = true;
		break;
	    }
	    tmp = tmp.ascend();
	}

	if(found){
	    int i = 0;
	    if(params.size() != sym.nrOfParams())
		return "semantic error";

	    //System.out.println("found");
	    for(Exp e : params){
		res = e.semanticChecker(table);
		if (res == null) return "semantic error";

		//System.out.println("In for loop");

		if (i < sym.nrOfParams()){
		    String parName = sym.getParam(i);
		    //System.out.println("parName :" + sym.getParam(i) + " sym.name: " + sym.name);

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
			    //  System.out.println("sym.name : " + sym.name + " sym.paramsize(): " + sym.nrOfParams());
			    return "semantic error";
			}
       			
		    }else{
			//System.out.println("res.name: " + res.name + " param.name: " + param.name);
			//System.out.println("res.byref: " + res.byRef + " param.byref: " + param.byRef);

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
	    
	    //System.out.println("sym.name: " + sym.name);

	    return type;
	}
	//System.out.println("*********************");
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
