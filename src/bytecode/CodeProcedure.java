package bytecode;

import java.util.ArrayList;
import java.util.List;

import bytecode.instructions.Instruction;
import bytecode.instructions.JMP;
import bytecode.instructions.JMPFALSE;
import bytecode.instructions.JMPTRUE;
import bytecode.type.CodeType;

public class CodeProcedure {
	private String name;
	private CodeType returnType;
	private CodeFile codeFile;
	private List<String> parameterNames = new ArrayList<String>();
	private List<CodeType> parameterTypes = new ArrayList<CodeType>();
	private List<String> variableNames = new ArrayList<String>();
	private List<CodeType> variableTypes = new ArrayList<CodeType>();
	private List<Instruction> instructions = new ArrayList<Instruction>();

	public CodeProcedure(String name, CodeType returnType, CodeFile codeFile) {
		this.name = name;
		this.returnType = returnType;
		this.codeFile = codeFile;
	}

	public void addParameter(String name, CodeType type) {
		this.parameterNames.add(name);
		this.parameterTypes.add(type);
	}

	public void addLocalVariable(String name, CodeType type) {
		this.variableNames.add(name);
		this.variableTypes.add(type);
	}

	public int addInstruction(Instruction instruction) {
		this.instructions.add(instruction);
		return this.instructions.size()-1;
	}
	public void replaceInstruction(int place, Instruction instruction) {
		this.instructions.remove(place);
		this.instructions.add(place, instruction);
	}

	public int addStringConstant(String value) {
		return this.codeFile.addStringConstant(value);
	}

	public int variableNumber(String name) {
		for (int i=0; i<parameterNames.size(); i++){
			if(name.equals(parameterNames.get(i))){
				return i;
			}
		}
		for (int i=0; i<this.variableNames.size(); i++){
			if(name.equals(this.variableNames.get(i))){
				return this.parameterNames.size() + i;
			}
		}
		return -1;
	}

	public int globalVariableNumber(String name) {
		return this.codeFile.globalVariableNumber(name);
	}

	public int procedureNumber(String name) {
		return this.codeFile.procedureNumber(name);
	}

	public int structNumber(String name) {
		return this.codeFile.structNumber(name);
	}

	public int fieldNumber(String structName, String varName) {
		return this.codeFile.fieldNumber(structName, varName);
	}

	public String getName() {
		return name;
	}

	private void moveJmps() {
		List<Instruction> newInstructions = new ArrayList<Instruction>();
		for(int i=0; i<this.instructions.size(); i++){
			Instruction instruction = this.instructions.get(i);
			if(instruction instanceof JMP){
				newInstructions.add(i, new JMP(findSize(((JMP)instruction).getJumpTo())));
			} else if(instruction instanceof JMPFALSE){
				newInstructions.add(i, new JMPFALSE(findSize(((JMPFALSE)instruction).getJumpTo())));
			} else if(instruction instanceof JMPTRUE){
				newInstructions.add(i, new JMPTRUE(findSize(((JMPTRUE)instruction).getJumpTo())));
			} else {
				newInstructions.add(instruction);
			}
		}
		this.instructions = newInstructions;
	}

	private int findSize(int num) {
		int pos = 0;
		for(int i=0; i<num; i++){
			pos += this.instructions.get(i).size();
		}
		return pos;
	}

	public byte[] getBytecode() {
		
		moveJmps();
		
		int totalSize = 0;
		byte[][] parameterTypesBytes = new byte[this.parameterTypes.size()][];
		for(int i=0; i<this.parameterTypes.size(); i++){
			parameterTypesBytes[i] = this.parameterTypes.get(i).getBytecode();
			totalSize += parameterTypesBytes[i].length;
		}
		byte[][] variableTypesBytes = new byte[this.variableTypes.size()][];
		for(int i=0; i<this.variableTypes.size(); i++){
			variableTypesBytes[i] = this.variableTypes.get(i).getBytecode();
			totalSize += variableTypesBytes[i].length;
		}
		byte[][] instructionsBytes = new byte[this.instructions.size()][];
		for(int i=0; i<this.instructions.size(); i++){
			instructionsBytes[i] = this.instructions.get(i).getBytecode();
			totalSize += instructionsBytes[i].length;
		}

		// Add size of name (2) counters (3*2) => 8
		totalSize += 8;
		byte[] nameBytes = this.name.getBytes();
		totalSize+=nameBytes.length;
		byte[] typeBytes = this.returnType.getBytecode();
		totalSize+=typeBytes.length;

        byte[] bytes = new byte[totalSize];
        NumberConversion.shortToByteArray(bytes,  0, (short) nameBytes.length);
        NumberConversion.shortToByteArray(bytes,  2, (short) this.parameterTypes.size());
		NumberConversion.shortToByteArray(bytes,  4, (short) this.variableTypes.size());
		NumberConversion.shortToByteArray(bytes,  6, (short) this.instructions.size());

        int index = 8;
        insert(bytes, nameBytes, index);
		index+=nameBytes.length;
		insert(bytes, typeBytes, index);
		index+=typeBytes.length;

		
        // Parameters
        // Only the values
        for(int i=0;i<parameterTypesBytes.length;i++){
        	insert(bytes, parameterTypesBytes[i], index);
        	index+=parameterTypesBytes[i].length;
        }
        // Variables
        // Only the values
        for(int i=0;i<variableTypesBytes.length;i++){
        	insert(bytes, variableTypesBytes[i], index);
        	index+=variableTypesBytes[i].length;
        }
        // Instructions
        // Only the values
        for(int i=0;i<instructionsBytes.length;i++){
        	insert(bytes, instructionsBytes[i], index);
        	index+=instructionsBytes[i].length;
        }

		return bytes;
	}

	private void insert(byte[] bytes, byte[] insert, int index) {
		for(int i=0;i<insert.length;i++){
			bytes[index + i] = insert[i];
		}
	}
}
