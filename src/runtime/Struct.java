package runtime;

import bytecode.type.CodeType;

public class Struct {
	public String name;
	public CodeType[] types;
	public Struct(String name, CodeType[] types) {
		this.name = name;
		this.types = types;
	}
	public String toString(Interpreter interpreter) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.name + "\n");
		for(int i=0;i<this.types.length;i++){
			buffer.append("    " + i + ": " + this.types[i].toString(interpreter));
			if(i<this.types.length-1) buffer.append("\n");
		}
		buffer.append("");
		return buffer.toString();
	}
	public int size(){
		return this.types.length;
	}
}
