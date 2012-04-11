package bytecode.instructions;

import runtime.Interpreter;

public class NOT extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{16};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "not";
	}
}
