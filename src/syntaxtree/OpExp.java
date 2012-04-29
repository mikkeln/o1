package syntaxtree;
import java.util.List;

public class OpExp extends Exp{

  String opname;
  Exp e1;
  Exp e2;
  String op;
  
  public OpExp (String opname, Exp e1, Exp e2, String op) {
    this.opname = opname;
    this.e1 = e1;
    this.e2 = e2;
    this.op = op;
  }

@Override
    public SymbolTable semanticChecker(SymbolTable table){
    SymbolTable tmp, located;

    //if not exp
    if(e2 == null && e1 != null){ //opname.equal(NOT); NOT
	SymbolTable t;

	t = e1.semanticChecker(table);

	if(!t.type.equals("bool"))
	    return null;
	else 
	    return t;
    }


    if(opname.equals("NEW")){ //NEW
	tmp = table;
	
	//check this level and higher
	while (tmp != null){
	    if ((located =tmp.locateWithinScope(op)) != null){ //found symbol
		return located;
	    }
	    tmp = tmp.ascend();
	}
	return null;
    }


    if(opname.equals("REL_OP")){ //if rel exp
	if(e1 == null || e2 == null) return null;

	SymbolTable res1, res2;

	res1 = e1.semanticChecker(table);
	res2 = e2.semanticChecker(table);

	SymbolTable b = new SymbolTable("bool", "not valid");

	if(res1.type.equals(res2.type)){
	    return b;//return bool
	}else if(res1.type.equals("float") && res2.type.equals("int")){
	    return b;//return bool
	}else{
	    return null;
	}
    }


    if(opname.equals("ARIT_OP")){//ARIT OP
	if (e1 == null || e2 == null) return null;

	SymbolTable res1, res2;

	res1 = e1.semanticChecker(table);
	res2 = e2.semanticChecker(table);


	if(res1.type.equals(res2.type)){
	    return res1;
	}else if(res1.type.equals("float") && res2.type.equals("int")){
	    return res1;
	}else{
	    return null;
	}
    }



    /* if(e1 == null || e2 == null)
	return null;
    
    if (e1.semanticChecker(table) == "semantic error" || e1.semanticChecker(table) == "synax error")
	return e1.semanticChecker(table);

    if (e2.semanticChecker(table) == "semantic error" || e2.semanticChecker(table) == "synax error")
	return e2.semanticChecker(table);

    if(opname == "REL_OP"){
	//return "bool";
	}*/

    return null;
}


  @Override
  public String printAst(int offset) {
    String out = "(" + opname + " " + op + " ";
    if (e1 != null || e2 != null) {
      if (e1 != null) out += "\n" + spaces(offset+2) + e1.printAst(offset + 2);
      if (e2 != null) out += "\n" + spaces(offset+2) + e2.printAst(offset + 2);
      out += "\n" + spaces(offset);
    }
    out += ")";
      
    return out;
  }
}
