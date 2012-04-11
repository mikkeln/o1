package bytecode.instructions;

import runtime.Interpreter;

public class NOP extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{15};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "nop";
	}
}
