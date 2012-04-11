package bytecode.instructions;

import runtime.Interpreter;

public class NEQ extends Instruction{
	public byte[] getBytecode() {
		// TODO: Move this instruction to a lower number!
		return new byte[]{32};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "neq";
	}
}
