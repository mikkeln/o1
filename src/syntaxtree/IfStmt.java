package syntaxtree;
import java.util.List;
import bytecode.CodeFile;
import bytecode.CodeStruct;
import bytecode.CodeProcedure;
import bytecode.instructions.*;

public class IfStmt extends Stmt {

  Exp exp;
  List<Stmt> if_stmt;
  List<Stmt> else_stmt;

  public IfStmt(Exp exp, List<Stmt> if_stmt, List<Stmt> else_stmt){
    this.exp = exp;
    this.if_stmt = if_stmt;
    this.else_stmt = else_stmt;
  }

  @Override
      public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc, SymbolTable table){
    System.out.println("If statement");
    int jumpinstr1 = 0, jumpinstr2 = 0; // We might need 2 jump offsets.
    
    //Generate code for exp
    exp.generateCode(file, null, proc);

    // NOTE: We expect true/false on stack after this! (do we need to generate it?)
    // We need a jump here if the expr is false, but it needs a value we don't 
    // have yet. We'll add it later using replaceInstruction.
    jumpinstr1 = proc.addInstruction(new NOP());
    
    if(if_stmt != null){
	    for(Stmt s : if_stmt){
		s.generateCode(file, null, proc, table);	  
	    }
	    // If there is an else-part this instruction gets replaced with a jump 
	    // to the end of the if-block.
	    jumpinstr2 = proc.addInstruction(new NOP()); 
	  }
	  
	  // Label for the false/else part of the IF. 
    int falsepos = proc.addInstruction(new NOP()); // Create label here
    
    // We inject a conditional jump here into the jumpinstr1 index
    proc.replaceInstruction(jumpinstr1, new JMPFALSE(falsepos));
    
    // ELSE starts here
	  if(else_stmt != null){
	    for(Stmt s : else_stmt){
		s.generateCode(file, null, proc, table);
	    }
	    
	   	// Label for the end of the IF. 
      int endpos = proc.addInstruction(new NOP()); // Create label here
    
      // We inject a unconditional jump here into the jumpinstr2 index
      proc.replaceInstruction(jumpinstr2, new JMP(endpos));
	  }
    
  }
	


    public String semanticChecker(SymbolTable table, String type){
	SymbolTable eres;
	String one, two;
	//String eres;
	String test;

	if(exp == null)
	    return "syntax error";

	eres = exp.semanticChecker(table);
	//Check that bool
	if(eres.type != "bool")
	    return "semantic error";

	if(if_stmt != null){
	    for(Stmt s : if_stmt){
		if(s == null) return "syntax error";
		one = s.semanticChecker(table, type);
		if(one == "semantic error") return one;
	
	    } 
	}

	if(else_stmt != null){
	    for(Stmt s : else_stmt){
		if(s == null) return "syntax error";
		two = s.semanticChecker(table, type);
		if(two == "semantic error") return two;
	    } 
	}

	return "no error";
    }





  public String printAst(int offset) {
    String out;
    out = spaces(offset) + "(IF_STMT " +  exp.printAst(offset + 2) + " \n";
    
    if (if_stmt != null) {
      out += spaces(offset+2) + "(\n";
      for (Stmt is : if_stmt){
        out += is.printAst(offset + 4);
      }
      out += spaces(offset+2) + ")\n";
    }
    
    out += "\n" + spaces(offset) + ")";
    if (else_stmt != null) {
      out += " ELSE (\n";
      for (Stmt es : else_stmt){
        out += es.printAst(offset + 4);
      }
      out += spaces(offset) + ")\n" + spaces(offset);
    } else {
      out += "\n";
    }

    
    return out;
  }

}
