package syntaxtree;
import java.util.List;

public class LiteralExp extends Exp {

  String litname;
  String value;
  
  public LiteralExp (String litname, String value) {
    this.litname = litname;
    this.value = value;
  }



    @Override
    public SymbolTable semanticChecker(SymbolTable table){

	//	System.out.println("LITERALEXP");
	SymbolTable sym = new SymbolTable(litname.toLowerCase(), value);

	/*	if(litname.equals("string")){
	    sym = new SymbolTable(value, litname.toLowerCase());
	    return sym;
	    }*/

	return sym;
    }



  @Override
  public String printAst(int offset) {
    return "(" + litname + "_LITERAL " + value + ")";
  }
}
