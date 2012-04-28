package syntaxtree;
import java.util.List;

public class CallExp extends Exp {

  String name;
  List<Exp> params;

  public CallExp(String name, List<Exp> params){
    this.name = name;
    this.params = params;
  }


@Override
    public SymbolTable semanticChecker(SymbolTable table){
	SymbolTable tmp, sym = table;
	Boolean found = false;
	SymbolTable res;

	//System.out.println("************call exp : name " + name);

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
	    //System.out.println("found");
	    if(params.size() != sym.nrOfParams())
		return null;

	    for(Exp e : params){
		res = e.semanticChecker(table);
		if(res == null) return null;
		if (i < sym.nrOfParams()){

		    String parName = sym.getParam(i);
		    //System.out.println("parName: " + parName + " sym.name " + sym.name);
		    
		    SymbolTable param = sym.locateWithinScope(parName);
		    if(param == null){
			//if(sym.name.equals())



			//System.out.println("param was null +++++++");
			return null;
		    }else{
			//System.out.println("res.name: " + res.name + " param.name: " + param.name);
			//System.out.println("res.byref: " + res.byRef + " param.byref: " + param.byRef);

			if(!param.type.equals(res.type))
			    return null;

			if(!(param.byRef == res.byRef))
			    return null;
		    }


		    /*	    if(!res.type.equals(sym.getParam(i))){
			if(res.type.equals("int") && sym.getParam(i).equals("float"))
			    continue;
			
			//	System.out.println("here! res: " + res + "sym.param" + sym.getParam(i));
			return null;
			}*/
		}else{

		    // System.out.println("hit else, params" + sym.nrOfParams() + " sym.name " + sym.name);
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
