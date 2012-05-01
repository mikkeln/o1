package syntaxtree;
import java.util.List;
import bytecode.*;
import bytecode.type.*;
import bytecode.instructions.*;

public class LiteralExp extends Exp {

  String litname;
  String value;
  
  public LiteralExp (String litname, String value) {
    this.litname = litname;
    this.value = value;
  }
    
    @Override
    public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc){
	System.out.println("LITERAL EXPRESSION");



    }




    @Override
    public SymbolTable semanticChecker(SymbolTable table){
	SymbolTable sym = new SymbolTable(litname.toLowerCase(), value);

	return sym;
    }



  @Override
  public String printAst(int offset) {
    return "(" + litname + "_LITERAL " + value + ")";
  }
}
