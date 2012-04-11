package bytecode.type;

import runtime.Interpreter;

public class VoidType extends CodeType {
	public static VoidType TYPE = new VoidType();
	private VoidType(){}
	public byte[] getBytecode() {
		return new byte[]{0};
	}
	public String toString(Interpreter interpreter) {
		return "void";
	}
	public Object initialValue() {
		return null;
	}
}
