package bytecode.instructions;

import runtime.Interpreter;

public class ADD extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{1};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "add";
	}
}
