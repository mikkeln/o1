package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class LOADLOCAL extends Instruction{
	private int varNum;
	public LOADLOCAL(int varNum) {
		this.varNum = varNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{12, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.varNum);
		return res;
	}
	public int size() {
		return 3;
	}
	public String toString(Interpreter interpreter) {
		return "loadlocal " + this.varNum + "";
	}
}
