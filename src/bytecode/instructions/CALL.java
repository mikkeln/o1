package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class CALL extends Instruction{
	private Integer funcNum;
	public CALL(Integer funcNum) {
		this.funcNum = funcNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{3, 0, 0};
		NumberConversion.shortToByteArray(res, 1, this.funcNum.shortValue());
		return res;
	}
	public int size() {
		return 3;
	}
	public String toString(Interpreter interpreter) {
		return "call " + interpreter.procedures[this.funcNum].name + " {" + this.funcNum + "}";
	}
}
