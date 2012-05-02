package syntaxtree;
import java.util.List;
import bytecode.CodeFile;
import bytecode.CodeStruct;
import bytecode.CodeProcedure;
import bytecode.instructions.*;

public class WhileStmt extends Stmt {

  Exp exp;
  List<Stmt> while_stmt;

  public WhileStmt(Exp exp, List<Stmt> while_stmt){
    this.exp = exp;
    this.while_stmt = while_stmt;
  }

  @Override
      public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc, SymbolTable table){
    
    int startlabel = proc.addInstruction(new NOP()); // Create label here
    
    // Get exp on stack
    exp.generateCode(file, null, proc);
    
    
    // This will get replaced with a proper jump instruction
    // once we know the end of the block
    int jumpinstr1 = proc.addInstruction(new NOP());
    
    if(while_stmt != null){
	    for(Stmt s : while_stmt){
		s.generateCode(file, null, proc, table);  
		  }
	  }
	  
    proc.addInstruction(new JMP(startlabel)); // Back to start (next iteration)
    
    int endlabel = proc.addInstruction(new NOP()); // Create label here
    proc.replaceInstruction(jumpinstr1, new JMPFALSE(endlabel));
    
  }


    @Override
	public String semanticChecker(SymbolTable table, String type){
	SymbolTable res;	
	
	if(exp == null)
	    return "syntax error";
		
	
	res = exp.semanticChecker(table);
	
	if(res.type != "bool")
	    return "semantic error";


	if(while_stmt != null){
	    for(Stmt s : while_stmt){
		if(s == null) return "syntax error";
		s.semanticChecker(table, type);	       
	    }
	}
	return "no error";
    }





  public String printAst(int offset) {
    String out;
    out = spaces(offset) + "(WHILE_STMT ( " +  exp.printAst(offset + 2) + " )\n";
    
    if (while_stmt != null) {
      for (Stmt is : while_stmt){
        out += is.printAst(offset + 4);
      }
    }    
    out += "\n" + spaces(offset) + ")";

    return out;
  }

}
