package bytecode.type;

import runtime.Interpreter;

public class FloatType extends CodeType {
	public static FloatType TYPE = new FloatType();
	private FloatType(){}
	public byte[] getBytecode() {
		return new byte[]{3};
	}
	public String toString(Interpreter interpreter) {
		return "float";
	}
	public Object initialValue() {
		return new Float(0);
	}
}
