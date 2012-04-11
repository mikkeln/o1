package bytecode.instructions;

import runtime.Interpreter;

public class EXP extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{5};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "exp";
	}
}
