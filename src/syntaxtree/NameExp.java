package syntaxtree;
import java.util.List;
import bytecode.*;
import bytecode.type.*;
import bytecode.instructions.*;


public class NameExp extends Exp{

    String name;
    Exp e1 = null;
    Boolean ref = false;
  
    public NameExp(String name, Boolean ref) {
    this.name = name;
    this.ref = ref;
  }
  
    public NameExp(String name, Exp e1, Boolean ref) {
    this.name = name;
    this.e1 = e1;
    this.ref = ref;
  }

  @Override
  public String getName(){
    return name;
  }

    @Override
    public String gete1Name(){
	if(e1 != null)
	    return e1.getName();
	else
	    return "";
   }

    @Override
	public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc, SymbolTable table){

	if(e1 == null){ //No . reference
	    if(proc != null){
		System.out.println("NAME EXPRESSION FOR '" +  this.name + "' ");
		int tester = proc.variableNumber(name);

		if(tester != -1){
		    proc.addInstruction(new LOADLOCAL(proc.variableNumber(name)));
		    System.out.println("nameexptest: " + proc.variableNumber(name));
		}else{
		    proc.addInstruction(new LOADGLOBAL(file.globalVariableNumber(name)));
		}

		//proc.addInstruction(new STORELOCAL(proc.variableNumber(name)));
	    }
	}
	else{ //. reference
	    if(proc != null){
		System.out.println("NAME EXPRESSION FOR '" + this.name + "' WITH REFERENCE");
		
		String structName = e1.getName();

		
		SymbolTable tmp = table.searchThru(structName);

		//System.out.println("NAMEEXP: " + structName + " " + tmp.type);
		
		proc.addInstruction(new LOADLOCAL(proc.variableNumber(structName))); //Add struct on top of stack
		// doesnt work! -but should?


		proc.addInstruction(new GETFIELD(proc.fieldNumber(tmp.type, name), 
						 proc.structNumber(tmp.type))); //leaving the field value on the stack
	    }   
	}
    }




@Override
    public SymbolTable semanticChecker(SymbolTable table){

    SymbolTable sym, local, tmp;
    SymbolTable varname;


    if(e1 != null){ //reference dot

	varname = e1.semanticChecker(table);
	if(varname == null)
	    return null;


	SymbolTable top = table;
	while(top.ascend() != null){
	    top = top.ascend();
	}


	SymbolTable objtype;
	SymbolTable st = table;
	SymbolTable vartype;
	sym = table;
	while(sym != null){
	    if((local = sym.locateWithinScope(varname.name)) != null){//Found varible

		st = local;
		
		while(st != null){ 
		    if((objtype = st.locateWithinScope(varname.type)) != null){ //find object type
			if((vartype = objtype.locateWithinScope(name)) != null){
			    return vartype;
			}else{
			    return null;
			}
		    }else{
			st = st.ascend();
			if(st == null){
			    return null;
			}
		    }
		}

	    }else{
		sym = sym.ascend();
	    }
	}

	return null;
    }

    tmp = table;

    while (tmp != null){
	if ((sym = tmp.locateWithinScope(name)) != null){

	    if(ref){
		SymbolTable temporary = new SymbolTable(sym.type, sym.name);
		temporary.byRef = ref;
		return temporary;
	    }

	    return sym; //return name so we can find it in callexp and check ref

	}
	tmp = tmp.ascend();
    }
    return null; //semantic error
}


  @Override
  public String printAst(int offset) {
    String out = "";
    
    if (e1 == null){
      out = "(NAME " + name + ")";
    } else {
      out = "( . (NAME " + name + ") "+ e1.printAst(offset) +")"; 
    }
      
    return out;
  }
}
