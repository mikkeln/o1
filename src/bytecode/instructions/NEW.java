package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class NEW extends Instruction{
	private Integer structNum;
	public NEW(Integer structNum) {
		this.structNum = structNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{14, 0, 0};
		NumberConversion.shortToByteArray(res, 1, this.structNum.shortValue());
		return res;
	}
	public int size() {
		return 3;
	}
	public String toString(Interpreter interpreter) {
		return "new " + interpreter.structs[this.structNum].name + "";
	}
}
