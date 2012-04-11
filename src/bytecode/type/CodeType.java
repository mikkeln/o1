package bytecode.type;

import runtime.Interpreter;

public abstract class CodeType {
	public abstract byte[] getBytecode();
	public abstract String toString(Interpreter interpreter);
	public abstract Object initialValue();
}
