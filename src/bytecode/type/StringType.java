package bytecode.type;

import runtime.Interpreter;

public class StringType extends CodeType {
	public static StringType TYPE = new StringType();
	private StringType(){}
	public byte[] getBytecode() {
		return new byte[]{5};
	}
	public String toString(Interpreter interpreter) {
		return "string";
	}
	public Object initialValue() {
		return "";
	}
}
