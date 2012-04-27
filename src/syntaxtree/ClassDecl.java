package syntaxtree;
import java.util.List;

public class ClassDecl extends Decl{
    
    String name;
    List<Decl> vlist;
    
    public ClassDecl (String name, List<Decl> vlist) {
	this.name = name;
	this.vlist = vlist;
    }
    
    @Override
	public String semanticChecker(SymbolTable table){
	
	//Check symboltable /entire table?
	SymbolTable top = table;
	while(top.parent != null)
	    top = top.parent;

	if(top.locate(name) != null)
	    return "semantic error";

	//Add entry to table
	SymbolTable tmp = table.newEntry(name, name); //should type be class?
      
	//Recursive call down
	if (vlist != null) {
	    for (Decl v: vlist){
		System.out.println("...in for...");
		if(v == null)
		    return "syntax error"; //syntax error

		if(v.semanticChecker(tmp) == null || v.semanticChecker(tmp) == null)
		   return "semantic error"; //Some error
	    }
	}    

	//if(tmp.locateWithinScope("Attr") != null)
	    // System.out.println("exists in scope");

	return "no error"; //All GOOOOOOOOOOD!
    }
    

    @Override
	public String printAst(int offset) {
	
	String out = spaces(offset) + "(CLASS_DECL (NAME " + name + ")\n";
	if (vlist != null) {
	    for (Decl v: vlist){
		if (v == null) {
		    out += "NULL ERROR IN ClassDecl\n";
		    continue;
		}
		out+= v.printAst(offset + 2);
		//v.syntaxChecker(table);
	    }
	}
	out += spaces(offset) + ")\n";
	
	return out;
    }
    

}

