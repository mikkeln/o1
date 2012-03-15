package syntaxtree;

public class ReturnStmt extends Stmt{

Exp exp;

	public ReturnStmt(Exp exp){
		this.exp = exp;
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

