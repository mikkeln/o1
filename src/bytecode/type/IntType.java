package bytecode.type;

import runtime.Interpreter;

public class IntType extends CodeType {
	public static IntType TYPE = new IntType();
	private IntType(){}
	public byte[] getBytecode() {
		return new byte[]{4};
	}
	public String toString(Interpreter interpreter) {
		return "int";
	}
	public Object initialValue() {
		return new Integer(0);
	}
}
