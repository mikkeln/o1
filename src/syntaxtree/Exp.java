package syntaxtree;
import bytecode.*;
import bytecode.type.*;
import bytecode.instructions.*;

public class Exp {

  public String printAst(int offset) {
    return "EXP OBJECT HAS NO STR DEF!";
  }
  

    public void generateCode(CodeFile file, CodeStruct struct, CodeProcedure proc){
	System.out.println("OVERRIDE EXP YOU BASTARDS, FUCKIN' SNOW");


    }


    public SymbolTable semanticChecker(SymbolTable table){
	return table;
    }

  public String getName(){
    return "";
  }

  public String spaces(int size){
    String out = "";
    for (int i = 0; i < size; i++) out += " ";
    return out;
  }
}
