package bytecode.type;

import runtime.Interpreter;

public class BoolType extends CodeType {
	public static BoolType TYPE = new BoolType();
	private BoolType(){}
	public byte[] getBytecode() {
		return new byte[]{2};
	}
	public String toString(Interpreter interpreter) {
		return "bool";
	}
	public Object initialValue() {
		return Boolean.FALSE;
	}
}
