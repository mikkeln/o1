package runtime;

import bytecode.type.CodeType;

public class Variable {
	public String name;
	public CodeType type;
	public Variable(String name, CodeType type) {
		this.name = name;
		this.type = type;
	}
	public String toString(Interpreter interpreter) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("var ");
		buffer.append(this.type.toString(interpreter));
		buffer.append(" ");
		buffer.append(this.name);
		return buffer.toString();
	}
}
