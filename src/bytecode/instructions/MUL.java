package bytecode.instructions;

import runtime.Interpreter;

public class MUL extends Instruction{
	public byte[] getBytecode() {
		// TODO: Move this instruction to a lower number!
		return new byte[]{34};
	}
	public int size() {
		return 1;
	}
	public String toString(Interpreter interpreter) {
		return "mul";
	}
}
