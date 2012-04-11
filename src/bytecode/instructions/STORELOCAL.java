package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class STORELOCAL extends Instruction{
	private int varNum;
	public STORELOCAL(int varNum) {
		super();
		this.varNum = varNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{26, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.varNum);
		return res;
	}
	public int size() {
		return 3;
	}
	public String toString(Interpreter interpreter) {
		return "storelocal " + this.varNum + "";
	}
}
