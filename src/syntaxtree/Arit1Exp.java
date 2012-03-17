package syntaxtree;

public class Arit1Exp extends Exp{

String arit;
Exp e1;
Exp e2;

	public Arit1Exp(Exp e1, Exp e2, String arit){
	this.e1 = e1;
	this.e2 = e2;
	this.arit = arit;
	}


}
