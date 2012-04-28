package syntaxtree;
import java.util.List;
import bytecode.CodeFile;

public class Program {
	
  List<Decl> decls;
  SymbolTable table;

  public Program(List<Decl> decls) {
    this.decls = decls;
    table = new SymbolTable("MAINTABLE", "MAINTABLE");
  }
  
  public void generateCode(CodeFile file) {
    System.out.println("MAKIN' BACON");
    
    for (Decl decl : decls) {
      if (decl == null) { 
        System.out.println("NULL ERROR IN CODE GEN!\n");
      }
      
      decl.generateCode(file);
    }
    
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

	if(table.locate("printfloat") != null)
	    System.out.println("success");

    }



    public String semanticChecker(){
      String res;
      int counter = 0;
      Boolean hasMain = false;

      addBuiltInn(table);

      for (Decl d : decls){
	  // System.out.println("COUNTER : " + counter++);
        if (d == null){
            return "syntax error"; //syntax error
        }

	res = d.semanticChecker(table);		

	if(res.equals("semantic error") || res.equals("syntax error"))
	    return res;

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