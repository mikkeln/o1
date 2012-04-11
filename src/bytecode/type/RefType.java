package bytecode.type;

import bytecode.NumberConversion;
import runtime.Interpreter;
import runtime.NullReference;

public class RefType extends CodeType{
	private int structRef;
	public RefType(int structRef) {
		this.structRef = structRef;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{1, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.structRef);
		return res;
	}
	public String toString(Interpreter interpreter) {
		return "" + interpreter.structs[this.structRef].name + "";
	}
	public Object initialValue() {
		return NullReference.NULL;
	}
}
