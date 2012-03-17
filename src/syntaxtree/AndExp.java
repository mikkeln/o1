package syntaxtree;

public class AndExp extends Exp{

Exp e1;
Exp e2;

	public AndExp(Exp e1, Exp e2){
	this.e1 = e1;
	this.e2 = e2;
	}

@Override
  public String printAst(int offset) {
    String out = "( AND EXP";
    if (e1 != null) out += e1.printAst(offset + 2) + " ";
    if (e2 != null) out += e2.printAst(offset + 2) + " ";
    out += ")";
      
    return out;
  }



}
