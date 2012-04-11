package bytecode;

import java.util.ArrayList;
import java.util.List;


import bytecode.type.CodeType;

public class CodeStruct {
	private String name;
	private List<String> names = new ArrayList<String>();
	private List<CodeType> types = new ArrayList<CodeType>();
	public CodeStruct(String name) {
		this.name = name;
	}
	public void addVariable(String name, CodeType type) {
		this.names.add(name);
		this.types.add(type);
	} 
	public int fieldNumber(String name) {
		for(int i=0;i<this.names.size();i++){
			if(name.equals(this.names.get(i))) return i;
		}
		return -1;
	}
	public String getName() {
		return name;
	}
	public byte[] getBytecode() {
		int totalSize = 0;
		byte[][] typesBytes = new byte[this.types.size()][];
		for(int i=0; i<this.types.size(); i++){
			typesBytes[i] = this.types.get(i).getBytecode();
			totalSize += typesBytes[i].length;
		}
		byte[] nameBytes = this.name.getBytes();
		totalSize+=nameBytes.length;

		// Add size of name (2) and counters (2) => 4
		totalSize += 4;

		byte[] bytes = new byte[totalSize];
        NumberConversion.shortToByteArray(bytes,  0, (short) nameBytes.length);
        NumberConversion.shortToByteArray(bytes,  2, (short) this.types.size());

        int index = 4;
        insert(bytes, nameBytes, index);

		index += nameBytes.length;

        // Types
        // Only the values
        for(int i=0;i<typesBytes.length;i++){
        	insert(bytes, typesBytes[i], index);
        	index += typesBytes[i].length;
        }

        return bytes;
	}

	private void insert(byte[] bytes, byte[] insert, int index) {
		for(int i=0;i<insert.length;i++){
			bytes[index + i] = insert[i];
		}
	}
}
