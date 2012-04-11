package bytecode.instructions;

import runtime.Interpreter;

public class GT extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{7};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "gt";
	}
}
