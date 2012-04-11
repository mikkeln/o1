package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class GETFIELD extends Instruction{
	private int fieldNumber;
	private int structNum;
	public GETFIELD(int fieldNumber, int structNum) {
		this.fieldNumber = fieldNumber;
		this.structNum = structNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{6, 0, 0, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.fieldNumber);
		NumberConversion.shortToByteArray(res, 3, (short)this.structNum);
		return res;
	}
	public int size() {
		return 5;
	}
	public String toString(Interpreter interpreter) {
		return "getfield " + interpreter.structs[this.structNum].name + "[" + this.fieldNumber + "] {" +
			interpreter.structs[this.structNum].types[this.fieldNumber].toString(interpreter) + "}";
	}
}
