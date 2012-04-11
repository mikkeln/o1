package bytecode.instructions;

import runtime.Interpreter;

public class AND extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{2};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "and";
	}
}
