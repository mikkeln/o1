package runtime;

public class ActivationBlock {
	public Procedure procedure;
	public Object[] variables;
	public int pc = 0;
	public ActivationBlock(Procedure procedure) {
		this.procedure = procedure;
		this.variables = new Object[procedure.parameters.length +
		                            procedure.variables.length];
	}
}
