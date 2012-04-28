package syntaxtree;

public class Stmt {

  public String printAst(int offset) {
    return "STMT OBJECT HAS NO STR DEF!";
  }
   
    public String semanticChecker(SymbolTable table, String type){
	return "no error";
    }

    
  public String spaces(int size){
    String out = "";
    for (int i = 0; i < size; i++) out += " ";
    return out;
  }
}
