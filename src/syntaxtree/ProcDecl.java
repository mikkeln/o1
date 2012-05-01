package syntaxtree;
import java.util.List;
import bytecode.*;
import bytecode.type.*;
import bytecode.instructions.*;

public class ProcDecl extends Decl{

  String name;
  String type;
  List<Decl> parlist;
  List<Decl> decllist;
  List<Stmt> stmtlist;

  
  public ProcDecl (String name, String type, List<Decl> parlist, 
                    List<Decl> decllist, List<Stmt> stmtlist) {
    this.name = name;
    this.type = (type == null)? "void" : type; // No type implies void.
    this.parlist = parlist;
    this.decllist = decllist;
    this.stmtlist = stmtlist;
  }

    @Override
    public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc){
      System.out.println("PROC " + name + " (" + type + ")");
      file.addProcedure(this.name);
      CodeProcedure pro;
      
      if (this.type.equals("bool")) {
        pro = new CodeProcedure(this.name, BoolType.TYPE, file);
      } else if (this.type.equals("float")) {
        pro = new CodeProcedure(this.name, FloatType.TYPE, file);        
      } else if (this.type.equals("int")) {
        pro = new CodeProcedure(this.name, IntType.TYPE, file);        
      } else if (this.type.equals("void")) {
        pro = new CodeProcedure(this.name, VoidType.TYPE, file);        
      } else { // Everything else is ref
        pro = new CodeProcedure(this.name, new RefType(file.structNumber(this.type)), file);        
      } 
      
      // Do some recursion magic yo

      if(parlist != null){
	  for(Decl p : parlist){
	      if(p == null){
		  continue;
	      }
	      p.generateCode(file, null, pro);
	  }
      }
      
      if (decllist != null) {
        for (Decl d: decllist){
          if (d == null) {
            //out += "NULL ERROR IN ProcDecl\n";
            continue;
          }
          d.generateCode(file, null, pro);
        }
      }
    
      if (stmtlist != null) {
        for (Stmt s: stmtlist){
          if (s == null) {
            //out += "NULL ERROR IN ProcDecl\n";
            continue;
          }
          s.generateCode(file, null, pro);
        }
      }
      
      //Add final return and update the file
      // RETURN VALUE NEEDS TO BE ON STACK?
      //if main?
      //pro.addInstruction(new RETURN());
      file.updateProcedure(pro);
      
    }



    @Override
 public String semanticChecker(SymbolTable table){
	String res;
	Boolean haveRet = false, haveVal = false;
	SymbolTable tmp;

	//Check that return type exists
	if (!type.equals("void") && !type.equals("int") && 
	    !type.equals("bool") && !type.equals("string") && !type.equals("float")){

	    //check symboltable
	    tmp = table;
	    while(tmp != null){
		if (tmp.locateWithinScope(type) != null){
		    haveVal = true;
		    tmp = null;
		}else{ 
		    tmp = tmp.ascend();
		}
	    }
	    if(!haveVal) return "semantic error";
	}
	


	if(type.equals("void"))
	    haveRet = true;

	if(name.equals("Main")){
	    if(!type.equals("void")) return "semantic error";
	    
	    if(parlist != null) {
		if(parlist.size() > 0)
		    return "semantic error";
	    }
	}


	//Add proc to table, check entire table
	SymbolTable top = table;
	while(top.parent != null){
	    top = top.ascend();
	}

	if(table.locate(name) != null)//find symbol
	     return "semantic error"; //syntax error

	//add to current level
	SymbolTable proc = table.newEntry(type, name);



	//continue in proc
	if (parlist != null){
	    for(Decl p : parlist){
		if (p == null)
		    return "semantic error";
		if((res = p.semanticChecker(proc)).equals("semantic error"))
		    return "semantic error";

		proc.addParam(res);
	    }
	}



	
	if (decllist != null){
	    for(Decl d : decllist){
		if (d == null)
		    return "syntax error";
		if((res = d.semanticChecker(proc)).equals("semantic error"))
		    return "semantic error";
	    }
	}
	
	if (stmtlist != null){
	    for(Stmt s : stmtlist){
		if (s == null)
		    return "syntax error";

		res = s.semanticChecker(proc, type);
		if(res.equals("semantic error")){
		    return "semantic error";
		}
		if(res.equals("ret")) 
		    haveRet = true;
	    }
	}

	if(haveRet == false)
	    return "semantic error";
	
	return name; //No errors
  }


  @Override
  public String printAst(int offset) {
    
    String out = spaces(offset) + "(PROC_DECL (NAME " + name + ")(TYPE " + type + ")\n";
    if (parlist != null) {
      for (Decl p: parlist){
        if (p == null) {
          out += "NULL ERROR IN ProcDecl\n";
          continue;
        }
        out+= p.printAst(offset + 2);
      }
    }
    
    if (decllist != null) {
      out += "\n";
      for (Decl d: decllist){
        if (d == null) {
          out += "NULL ERROR IN ProcDecl\n";
          continue;
        }
        out+= d.printAst(offset + 2);
      }
    }
    
    if (stmtlist != null) {
      out += "\n";
      for (Stmt s: stmtlist){
        if (s == null) {
          out += "NULL ERROR IN ProcDecl\n";
          continue;
        }
        out+= s.printAst(offset + 2);
      }
    }
    
    
    
    out += "\n" + spaces(offset) + ")\n";
      
    return out;
  }
}
