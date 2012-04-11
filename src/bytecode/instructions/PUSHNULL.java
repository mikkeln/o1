package bytecode.instructions;

import runtime.Interpreter;

public class PUSHNULL extends Instruction{
	public byte[] getBytecode() {
		return new byte[]{21};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "pushnull";
	}
}
