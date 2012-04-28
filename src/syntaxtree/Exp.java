package syntaxtree;

public class Exp {

  public String printAst(int offset) {
    return "EXP OBJECT HAS NO STR DEF!";
  }
  

    public SymbolTable semanticChecker(SymbolTable table){
	return table;
    }



  public String spaces(int size){
    String out = "";
    for (int i = 0; i < size; i++) out += " ";
    return out;
  }
}
