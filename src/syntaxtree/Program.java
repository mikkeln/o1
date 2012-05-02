package syntaxtree;
import java.util.List;
import bytecode.CodeFile;
import bytecode.CodeProcedure;
import bytecode.type.*;
import bytecode.instructions.*;
import bytecode.*;


public class Program {
	
  List<Decl> decls;
  SymbolTable table;

  public Program(List<Decl> decls) {
    this.decls = decls;
    table = new SymbolTable("MAINTABLE", "MAINTABLE");
  }
  
  public void generateCode(CodeFile file) {
    System.out.println("MAKIN' BACON (Main program node)");
    
    file.addProcedure("printfloat");
    file.addProcedure("printstr");
    file.addProcedure("printint");
    file.addProcedure("printline");

    // Add libraries (I think this is how it's done...)
    CodeProcedure pstring = new CodeProcedure("printstr", VoidType.TYPE, file);
    pstring.addParameter("s", StringType.TYPE);
    file.updateProcedure(pstring);
    

    CodeProcedure pline = new CodeProcedure("printline", VoidType.TYPE, file);
    pline.addParameter("s", StringType.TYPE);
    file.updateProcedure(pline);

    
    CodeProcedure pint = new CodeProcedure("printint", VoidType.TYPE, file);
    pint.addParameter("i", IntType.TYPE);
    file.updateProcedure(pint);

    CodeProcedure pfloat = new CodeProcedure("printfloat", VoidType.TYPE, file);
    pfloat.addParameter("f", FloatType.TYPE);
    file.updateProcedure(pfloat);

    file.addProcedure("readline");
    file.addProcedure("readstr");
    file.addProcedure("readfloat");
    file.addProcedure("readint");
    file.addProcedure("readchar");
    
    CodeProcedure rline = new CodeProcedure("readline", StringType.TYPE, file);
    file.updateProcedure(rline);

    CodeProcedure rstr = new CodeProcedure("readstr", StringType.TYPE, file);
    file.updateProcedure(rstr);

    CodeProcedure rfloat = new CodeProcedure("readfloat", FloatType.TYPE, file);
    file.updateProcedure(rfloat);

    CodeProcedure rint = new CodeProcedure("readint", IntType.TYPE, file);
    file.updateProcedure(rint);

    CodeProcedure rchar = new CodeProcedure("readchar", IntType.TYPE, file);
    file.updateProcedure(rchar);



    // Generate code for the whole program
    for (Decl decl : decls) {
      if (decl == null) { 
        System.out.println("NULL ERROR IN CODE GEN!\n");
      }
      
      decl.generateCode(file, null, null, table);
    }
    
    //Set Main method
    file.setMain("Main");
    
  }

  public String printAst(){
    
    StringBuilder sb = new StringBuilder();
    sb.append("(Program\n");
    for (Decl decl : decls) {
      if (decl == null) { 
        sb.append("  NULL ERROR IN Program\n");
        continue;
      }
      
      sb.append(decl.printAst(2) + "\n");
      decl.semanticChecker(table);
    }
    sb.append(")");
    return sb.toString();
  }

    public void addBuiltInn(SymbolTable table){
	SymbolTable entry;
	entry = table.newEntry("string", "readline");
	
	entry = table.newEntry("void", "printint");
	entry.addParam("int");

	entry = table.newEntry("float", "printfloat");
	entry.addParam("float");

	entry = table.newEntry("string", "printstr");
	entry.addParam("string");

	entry = table.newEntry("string", "printline");
	entry.addParam("string");

	entry = table.newEntry("int", "readint");
	
	entry = table.newEntry("float", "readfloat");
	
	entry = table.newEntry("int", "readchar");
	
	entry = table.newEntry("string", "readstring");
	



    }



    public String semanticChecker(){
      String res;
      Boolean hasMain = false;

      addBuiltInn(table);

      for (Decl d : decls){
        if (d == null){
            return "syntax error"; //syntax error
        }

	res = d.semanticChecker(table);		

	if(res.equals("semantic error") || res.equals("syntax error")){
	    return res;
	}

	if(res.equals("Main")){ 
	    hasMain =  true;
	}
      }
      
      if(hasMain)
	  return "no error";
      else
	  return "semantic error";

  }


}
