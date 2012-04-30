package syntaxtree;
import bytecode.CodeFile;

public class Decl {


  public String printAst(int offset) {
    return "DECL OBJECT HAS NO STR DEF!";
  }
  

  public String semanticChecker(SymbolTable table){
      return "no error"; //No errors
  }



    public void generateCode(CodeFile file/*, CodeStruct struct, CodeProcedure proc*/) {
    System.out.println("DECL!");
  }
  

  public String spaces(int size){
    String out = "";
    for (int i = 0; i < size; i++) out += " ";
    return out;
  }
}
