package bytecode.instructions;

import runtime.Interpreter;

public class OR extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{17};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "or";
	}
}
