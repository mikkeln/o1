package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class LOADGLOBAL extends Instruction{
	private int varNum;
	public LOADGLOBAL(int varNum) {
		this.varNum = varNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{11, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.varNum);
		return res;
	}
	public int size() {
		return 3;
	}
	public String toString(Interpreter interpreter) {
		return "loadglobal " + interpreter.variables[this.varNum].name + "";
	}
}
