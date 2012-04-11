package bytecode.instructions;

import runtime.Interpreter;

public class GTEQ extends Instruction{
	public byte[] getBytecode() {
		// TODO: Move this instruction to a lower number!
		return new byte[]{31};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "gteq";
	}
}
