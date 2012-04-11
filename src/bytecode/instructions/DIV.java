package bytecode.instructions;

import runtime.Interpreter;

public class DIV extends Instruction{
	public byte[] getBytecode() {
		// TODO: Move this instruction to a lower number!
		return new byte[]{35};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "div";
	}
}
