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

	SymbolTable cc = table.parent;
	if(cc != null){
	    SymbolTable st = cc.locate("Foo");
	    if(st != null){
		SymbolTable ss = st.locate("Attr");
	    
		System.out.println("In Foo Attr exists: " + ss.name);

	    }
	}


	//System.out.println("Assign stmt");
		
       	vres = var.semanticChecker(table);
	if(vres == null)
	    return "semantic error"; //error

	System.out.println("got past first");

	eres = exp.semanticChecker(table);
	if(eres == null)
	    return "semantic error"; //error	

	System.out.println("DEBUG2 : vres: " + vres.type + " eres: " + eres.type);

	if(cc != null){
	    SymbolTable st = cc.locate("Foo");
	    if(st != null){
		SymbolTable ss = st.locate("Attr");
	    
		System.out.println("In Foo Attr exists: " + ss.name);

	    }
	}

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
