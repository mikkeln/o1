package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class PUSHSTRING extends Instruction{
	private int stringNum;
	public PUSHSTRING(int stringNum) {
		this.stringNum = stringNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{22, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.stringNum);
		return res;
	}
	public int size() {
		return 3;
	}
	public String toString(Interpreter interpreter) {
		return "pushstring \"" + interpreter.constants[this.stringNum] + "\"";
	}
}
