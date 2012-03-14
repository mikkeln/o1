package syntaxtree;

public class ReturnStmt extends Stmt{

Exp exp;

	public ReturnStmt(Exp exp){
		this.exp = exp;
	}


	public String printAst(int offset){
	  String out;
	  
	  if(exp == null)
	    out = "(RETURN_STMT)\n"+ spaces(offset);
	  else
	    out = "(RETURN_STMT " +  exp.printAst(offset + 2) + ")\n" + spaces(offset);

	  return out;
	}
	
}

