package syntaxtree;

public class NotExp extends Exp{

Exp e1;

	public NotExp(Exp e1){
	this.e1 = e1;
	
	}

@Override
  public String printAst(int offset) {
    String out = "(NOT EXP";
   if (e1 != null) out += e1.printAst(offset + 2) + " ";
     out += ")";
      
    return out;
  }



}
