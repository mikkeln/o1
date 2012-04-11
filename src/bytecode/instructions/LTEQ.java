package bytecode.instructions;

import runtime.Interpreter;

public class LTEQ extends Instruction{
	public byte[] getBytecode() {
		// TODO: Move this instruction to a lower number!
		return new byte[]{30};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "lteq";
	}
}
