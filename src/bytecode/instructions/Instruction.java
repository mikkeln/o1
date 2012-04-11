package bytecode.instructions;

import runtime.Interpreter;

public abstract class Instruction {
	public abstract byte[] getBytecode();
	public abstract int size();
	public abstract String toString(Interpreter interpreter);
}
