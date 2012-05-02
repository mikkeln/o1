package syntaxtree;
import java.util.List;
import bytecode.*;
import bytecode.type.*;
import bytecode.instructions.*;

public class AssignStmt extends Stmt{

  Exp var;
  Exp exp;

	public AssignStmt(Exp var, Exp exp){
  	this.var = var;
	  this.exp = exp;
	}


  @Override
  public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc){
    
      //We only need the name of this one, so I commented it out the old code
      //var.generateCode(file, null, proc);
      String varname = var.getName();
      String structName = var.gete1Name();
      
      System.out.println("ASSIGN VARIABLE: varname: " + varname + " structname: " + var.gete1Name());
      
      // I really hope this value gets pushed to stack!
   	      exp.generateCode(file, null, proc); 


      if(proc != null){ //If proc is delivered, assume the assignstmt is in a procedure
	  // Store whatever is on stack to local variable varname
	  if(structName.equals("")){
	      int crap = proc.addInstruction(new STORELOCAL(proc.variableNumber(varname)));
	      //try to get type
	      //proc.addInstruction(new PUSHSTRING(exp.getName()));
	      	      System.out.println("SAAAAAAAAAAAAAAP " + varname + " crap : " + crap);
	  }else{

	      System.out.println("WWWWWWWWWWTTTTTFFFFFFFFFFFF");
	      System.out.println(structName + " " + varname);
	      int tester = proc.addInstruction(new LOADLOCAL(proc.variableNumber(varname)));
	      System.out.println("tester: " + tester);
	      
	      //String ss = proc.addInstruction(new PUSHSTRING);
	      int index = proc.variableNumber(structName);
	      
	      // String varType = proc.variableTypes.get(index);
	      System.out.println("IS GOOOOd?: " + index);

	      proc.addInstruction(new PUTFIELD(proc.fieldNumber("Complex", varname), proc.structNumber("Complex"))); //How to get struct type?
	  }
      } else { // Global scope = global variable
	  // Not working!
	  //file.addInstruction(new STOREGLOBAL(file.variableNumber(varname)));
      }
      
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
