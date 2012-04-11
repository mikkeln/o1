package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class LOADOUTER extends Instruction{
	private int varNum;
	private int levels;
	public LOADOUTER(int varNum, int levels) {
		super();
		this.varNum = varNum;
		this.levels = levels;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{13, 0, 0, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.varNum);
		NumberConversion.shortToByteArray(res, 3, (short)this.levels);
		return res;
	}
	public int size() {
		return 5;
	}
	public String toString(Interpreter interpreter) {
		return "loadouter " + this.varNum + ", " + this.levels + "";
	}
}
