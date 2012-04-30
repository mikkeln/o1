package syntaxtree;
import java.util.List;
import bytecode.CodeFile;
import bytecode.CodeStruct;
public class ClassDecl extends Decl{
    
    String name;
    List<Decl> vlist;
    
    public ClassDecl (String name, List<Decl> vlist) {
	this.name = name;
	this.vlist = vlist;
    }
    
    @Override
	public String semanticChecker(SymbolTable table){
	String test;

	//Check symboltable /entire table?
	SymbolTable top = table;
	while(top.parent != null)
	    top = top.parent;

	if(top.locate(name) != null)
	    return "semantic error";

	//Add entry to table
	SymbolTable tmp = table.newEntry(name, name); 
      
	//Recursive call down
	if (vlist != null) {
	    for (Decl v: vlist){
		if(v == null)
		    return "syntax error"; //syntax error


		test = v.semanticChecker(tmp);
		if(test.equals("semantic error") || test.equals("syntax error")){
		    return "semantic error"; //Some error
		}
	    }
	}    
	return "no error"; //All GOOOOOOOOOOD!
    }


    public void generateCode(CodeFile file/*, CodeStruct struct, CodeProcedure proc*/) {
    System.out.println("CREATING STRUCT '" + this.name + "'");
    
    file.addStruct(this.name);
    CodeStruct struct = new CodeStruct(this.name);

    if (vlist != null) {
      for (Decl v: vlist){
        if (v == null) {
	    //      out += "NULL ERROR IN ClassDecl\n";
          continue;
        }

        // Add decl to to struct!
        // Ugly casting, but everything is stored as Decl :/
        ((VarDecl)v).generateCode(struct);

      }
    }
    
    file.updateStruct(struct);    
    System.out.println("DONE MAKING STRUCT '" + this.name + "'");
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

