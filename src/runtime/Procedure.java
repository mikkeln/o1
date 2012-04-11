package runtime;

import bytecode.instructions.Instruction;
import bytecode.type.CodeType;

public class Procedure {
	public String name;
	public CodeType returnType;
	public CodeType[] parameters;
	public CodeType[] variables;
	public Instruction[] instructions;
	public byte[] bytecode;
	public Procedure(String name, CodeType returnType, CodeType[] parameters, CodeType[] variables,
			Instruction[] instructions, byte[] bytecode) {
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
		this.variables = variables;
		this.instructions = instructions;
		this.bytecode = bytecode;
	}
	public String toString(Interpreter interpreter) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("func ");
		buffer.append(this.returnType.toString(interpreter));
		buffer.append(" ");
		buffer.append(this.name);
		buffer.append("(");
		for(int i=0;i<this.parameters.length;i++){
			CodeType type = this.parameters[i];
			buffer.append(type.toString(interpreter));
			buffer.append(" " + i);
			if(i<this.parameters.length-1) buffer.append(", ");
		}
		buffer.append(")");
		for(int i=0;i<this.variables.length;i++){
			CodeType type = this.variables[i];
			buffer.append("\n    var ");
			buffer.append(type.toString(interpreter));
			buffer.append(" " + (this.parameters.length + i));
//			if(i<this.variables.length-1) buffer.append(", ");
		}
		int pos = 0;
		for(int i=0;i<this.instructions.length;i++){
			Instruction instruction = this.instructions[i];
			buffer.append("\n    " + pos + ": ");
			buffer.append(instruction.toString(interpreter));
			pos += instruction.size();
//			if(i<this.parameters.length-1) buffer.append(", ");
		}
		return buffer.toString();
	}
}
