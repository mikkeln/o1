package syntaxtree;
import java.util.List;

public class AssignStmt extends Stmt{

Exp var;
Exp exp;

	public AssignStmt(Exp var, Exp exp){
	this.var = var;
	this.exp = exp;
	}

    @Override
	public String semanticChecker(SymbolTable table, String type){
	SymbolTable vres, eres;

	
       	vres = var.semanticChecker(table);
	if(vres == null)
	    return "semantic error"; //error


	eres = exp.semanticChecker(table);
	if(eres == null)
	    return "semantic error"; //error	


	if(vres.type.equals(eres.type)){
	    return vres.type;
	}else if(vres.type.equals("float") && eres.type.equals("int")){
	    return vres.type;
	}else{
	    return "semantic error";
	}

    }



	@Override
	public String printAst(int offset){
	String out;	

	out = spaces(offset) + "(ASSIGN_STMT\n"; 
	out+= spaces(offset + 2) + var.printAst(offset + 2) + "\n";
	
	out+= spaces(offset + 2) + exp.printAst(offset + 2) + "\n";

	out += spaces(offset) + ")\n";

	return out;
	}




}
