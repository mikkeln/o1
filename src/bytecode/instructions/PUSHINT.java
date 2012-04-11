package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class PUSHINT extends Instruction{
	private Integer value;
	public PUSHINT(Integer value) {
		this.value = value;
	}
	public byte[] getBytecode() {
		byte[] array = new byte[]{20, 0, 0, 0, 0};
		NumberConversion.intToByteArray(array, 1, this.value);
		return array;
	}
	public int size() {
		return 5;
	}
	public String toString(Interpreter interpreter) {
		return "pushint " + this.value + "";
	}
}
