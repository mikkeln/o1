package syntaxtree;
import java.util.List;

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
    public SymbolTable semanticChecker(SymbolTable table){

    SymbolTable sym, local, tmp;
    SymbolTable varname;

    //System.out.println("NAMEEXP name:" + name);

    if(e1 != null){ //reference dot

	varname = e1.semanticChecker(table);
	if(varname == null)
	    return null;

	System.out.println("******Varname : " +  varname.name + " name: " + name);

	SymbolTable top = table;
	while(top.ascend() != null){
	    top = top.ascend();
	}

	SymbolTable test = top.locate("Foo");
	if(test != null && test.locate("Attr") != null){
	System.out.println("Found attr");
	}else{
	    if(top.parent == null)
	    System.out.println("Not found");
	}

	SymbolTable objtype;
	SymbolTable st = table;
	SymbolTable vartype;
	sym = table;
	while(sym != null){
	    if((local = sym.locateWithinScope(varname.name)) != null){//Found varible
		System.out.println("-First score");
		st = local;
		
		while(st != null){ 
		    if((objtype = st.locateWithinScope(varname.type)) != null){ //find object type
			System.out.println("--Second score");
			if((vartype = objtype.locateWithinScope(name)) != null){
			    System.out.println("--Third score");
			    return vartype;
			}else{
			    System.out.println("---No third score");
			    return null;
			}
		    }else{
			System.out.println("--No second score,   st: " + st.name);
			st = st.ascend();
			if(st == null){
			    System.out.println("hmm" + st); 
			    return null;
			}
		    }
		    //return null;
		}

	    }else{
		System.out.println("+++++++Why here?");
		sym = sym.ascend();
	    }
	}

	return null;
    }

    tmp = table;

    //System.out.println("HERE");

    while (tmp != null){
	if ((sym = tmp.locateWithinScope(name)) != null){
	    /*if(sym.byRef){
		System.out.println("Should be by ref");
		return "semantic error";
		}else{*/
	    //	    System.out.println("fml - name : " + sym.name);

	    if(ref){
		SymbolTable temporary = new SymbolTable(sym.type, sym.name);
		temporary.byRef = ref;
		return temporary;
	    }

	    return sym; //return name so we can find it in callexp and check ref
	    // }

	}
	tmp = tmp.ascend();
    }
    //System.out.println("Not found");
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
