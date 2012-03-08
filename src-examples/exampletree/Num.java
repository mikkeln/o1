package exampletree;

public class Num extends Term{
	private Integer number = null;
	public Num(Integer number) {
		this.number = number;
	}
	public String toString(){
		return "(" + number +  ")";
	}
}
