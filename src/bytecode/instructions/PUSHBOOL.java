package bytecode.instructions;

import runtime.Interpreter;

public class PUSHBOOL extends Instruction{
	private boolean value;
	public PUSHBOOL(boolean value) {
		this.value = value;
	}
	public byte[] getBytecode() {
		return new byte[]{18, (byte) (this.value ? 1 : 0)};
	}
	public int size() {
		return 2;
	}
	public String toString(Interpreter interpreter) {
		return "pushbool " + this.value + "";
	}
}
