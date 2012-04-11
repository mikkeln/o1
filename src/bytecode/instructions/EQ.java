package bytecode.instructions;

import runtime.Interpreter;

public class EQ extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{4};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "eq";
	}
}
