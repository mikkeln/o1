package bytecode.instructions;

import bytecode.NumberConversion;
import runtime.Interpreter;

public class JMP extends Instruction{
	private int jumpTo;
	public JMP(int jumpTo) {
		this.jumpTo = jumpTo;
	}
	public byte[] getBytecode() {
		byte[] res = new byte[]{8, 0, 0};
		NumberConversion.shortToByteArray(res, 1, (short)this.jumpTo);
		return res;
	}
	public int size() {
		return 3;
	}
	public int getJumpTo() {
		return this.jumpTo;
	}
	public String toString(Interpreter interpreter) {
		return "jmp " + this.jumpTo;
	}
}
