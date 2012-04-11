package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class STOREGLOBAL extends Instruction{
	private int varNum;
	public STOREGLOBAL(int varNum) {
		super();
		this.varNum = varNum;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{25, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.varNum);
		return res;
	}
	public int size() {
		return 3;
	}
	public String toString(Interpreter interpreter) {
		return "storeglobal " + interpreter.variables[this.varNum].name +
			"{" + interpreter.variables[this.varNum].type.toString(interpreter) + "}";
	}
}
