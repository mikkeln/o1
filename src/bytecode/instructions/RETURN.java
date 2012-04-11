package bytecode.instructions;

import runtime.Interpreter;

public class RETURN extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{24};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "return";
	}
}
