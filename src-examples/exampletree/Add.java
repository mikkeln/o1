package exampletree;

public class Add extends Expr {
	private Expr expr = null;
	private Term term = null;
	public Add(Expr expr, Term term) {
		this.expr = expr;
		this.term = term;
	}
	public String toString(){
		return "(" + expr + "+" + term +  ")";
	}
}
