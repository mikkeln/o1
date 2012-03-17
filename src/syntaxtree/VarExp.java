package syntaxtree;

public class VarExp extends Exp{

String l;

	public VarExp(String l){
	this.l = l;
	
	}

@Override
  public String printAst(int offset) {
    String out = "( LITERAL EXP";
    if (l != null) out += l + " ";
     out += ")";
      
    return out;
  }



}
