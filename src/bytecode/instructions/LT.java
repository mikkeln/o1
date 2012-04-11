package bytecode.instructions;

import runtime.Interpreter;

public class LT extends Instruction{
	public byte[] getBytecode() {
		// TODO: Move this instruction to a lower number!
		return new byte[]{29};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "lt";
	}
}
