package syntaxtree;

public class Decl {

  public String printAst(int offset) {
    return "DECL OBJECT HAS NO STR DEF!";
  }
  
  public String spaces(int size){
    String out = "";
    for (int i = 0; i < size; i++) out += " ";
    return out;
  }
}