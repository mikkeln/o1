package syntaxtree;
import java.util.List;

public class AssignStmt extends Stmt{

Exp var;
Exp exp;

	public AssignStmt(Exp var, Exp exp){
	this.var = var;
	this.exp = exp;
	}


	public String printAst(int offset){
	String out;	

	out = spaces(offset) + "(ASSIGN_STMT\n"; 
	out+= spaces(offset + 2) + var.printAst(offset + 2) + "\n";
	
	out+= spaces(offset + 2) + exp.printAst(offset + 2) + "\n";

	out += spaces(offset) + ")\n";

	return out;
	}




}
