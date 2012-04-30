package syntaxtree;

public class ReturnStmt extends Stmt{

Exp exp;

	public ReturnStmt(Exp exp){
		this.exp = exp;
	}

    public String semanticChecker(SymbolTable table, String type){

	if (type != "void" && exp == null)
	    return "semantic error";

	if(type == "void" && exp != null)
	    return "semantic error";

	if(exp == null)
	    return "ret";

	SymbolTable res = exp.semanticChecker(table);

	if (res.type.equals("null")) return "ret";

	if (!type.equals(res.type)) return "semantic error";

	return "ret";
    }




	public String printAst(int offset){
	  String out;
	  
	  if(exp == null)
	    out = spaces(offset) + "(RETURN_STMT)\n";
	  else
	    out = spaces(offset) + "(RETURN_STMT " +  exp.printAst(offset + 2) + ")\n";

	  return out;
	}
	
}

