package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class PUSHFLOAT extends Instruction{
	private Float value;
	public PUSHFLOAT(Float value) {
		this.value = value;
	}
	public byte[] getBytecode() {
		byte[] array = new byte[]{19, 0, 0, 0, 0};
		NumberConversion.floatToByteArray(array, 1, this.value);
		return array;
	}
	public int size() {
		return 5;
	}
	public String toString(Interpreter interpreter) {
		return "pushfloat " + this.value + "";
	}
}
