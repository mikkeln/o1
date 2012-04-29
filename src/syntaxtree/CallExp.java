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
		if(res == null) return null;
		if (i < sym.nrOfParams()){

		    String parName = sym.getParam(i);
		    
		    SymbolTable param = sym.locateWithinScope(parName);
		    if(param == null){

			return null;
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
