package exampletree;

public class Mult extends Term{
	private Term term = null;
	private Num num = null;
	public Mult(Term term, Num num) {
		this.term = term;
		this.num = num;
	}
	public String toString(){
		return "(" + term + "*" + num +  ")";
	}
}
