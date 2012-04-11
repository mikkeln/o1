package runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import bytecode.NumberConversion;
import bytecode.instructions.ADD;
import bytecode.instructions.AND;
import bytecode.instructions.CALL;
import bytecode.instructions.DIV;
import bytecode.instructions.EQ;
import bytecode.instructions.EXP;
import bytecode.instructions.GETFIELD;
import bytecode.instructions.GT;
import bytecode.instructions.GTEQ;
import bytecode.instructions.Instruction;
import bytecode.instructions.JMP;
import bytecode.instructions.JMPFALSE;
import bytecode.instructions.JMPTRUE;
import bytecode.instructions.LOADGLOBAL;
import bytecode.instructions.LOADLOCAL;
import bytecode.instructions.LOADOUTER;
import bytecode.instructions.LT;
import bytecode.instructions.LTEQ;
import bytecode.instructions.MUL;
import bytecode.instructions.NEQ;
import bytecode.instructions.NEW;
import bytecode.instructions.NOP;
import bytecode.instructions.NOT;
import bytecode.instructions.OR;
import bytecode.instructions.POP;
import bytecode.instructions.PUSHBOOL;
import bytecode.instructions.PUSHFLOAT;
import bytecode.instructions.PUSHINT;
import bytecode.instructions.PUSHNULL;
import bytecode.instructions.PUSHSTRING;
import bytecode.instructions.PUTFIELD;
import bytecode.instructions.RETURN;
import bytecode.instructions.STOREGLOBAL;
import bytecode.instructions.STORELOCAL;
import bytecode.instructions.STOREOUTER;
import bytecode.instructions.SUB;
import bytecode.type.BoolType;
import bytecode.type.RefType;
import bytecode.type.CodeType;
import bytecode.type.FloatType;
import bytecode.type.IntType;
import bytecode.type.StringType;
import bytecode.type.VoidType;


public class Loader {
	private String filename;
	private Variable[] variables;
	private Procedure[] procedures;
	private Struct[] structs;
	private String[] constants;
	private int mainNum;
	public Loader(String filename) {
		this.filename = filename;
	}
	public void load() throws Exception {
		File file = new File(this.filename);
		byte[] bytes = bytesFromFile(file);
		
        this.mainNum   = NumberConversion.shortFromByteArray(bytes,  0);
        int varCnt    = NumberConversion.shortFromByteArray(bytes,  2);
        int procCnt   = NumberConversion.shortFromByteArray(bytes,  4);
        int structCnt = NumberConversion.shortFromByteArray(bytes,  6);
        int constCnt  = NumberConversion.shortFromByteArray(bytes,  8);

        int index = 10;
        // Variables
        // First the sizes
        int[] varSizes = new int[varCnt];
        for(int i=0; i<varCnt; i++){
        	varSizes[i] = NumberConversion.shortFromByteArray(bytes, index);
        	index += 2;
        }
        // Then the values
        String[] varNames = new String[varCnt];
        for(int i=0; i<varCnt; i++){
        	varNames[i] = name(bytes, index, varSizes[i]);
        	index += varSizes[i];
        }
        this.variables = new Variable[varCnt];
        for(int i=0; i<varCnt; i++){
        	CodeType type = codeType(bytes, index);
        	variables[i] = new Variable(varNames[i], type);
        	index += 1;
        	if(type instanceof RefType) index += 2;
        }

        // Procedures
        // First the sizes
        int[] procSizes = new int[procCnt];
        for(int i=0;i<procCnt;i++){
        	procSizes[i] = NumberConversion.shortFromByteArray(bytes, index);
        	index += 2;
        }
        // Then the values
        this.procedures = new Procedure[procCnt];
        for(int i=0;i<procCnt;i++){
        	this.procedures[i] = procedure(bytes, index, procSizes[i]);
        	index+=procSizes[i];
        }

        // Structs
        // First the sizes
        int[] structSizes = new int[structCnt];
        for(int i=0;i<structCnt;i++){
        	structSizes[i] = NumberConversion.shortFromByteArray(bytes, index);
        	index += 2;
        }
        // Then the values
        this.structs = new Struct[structCnt];
        for(int i=0;i<structCnt;i++){
        	this.structs[i] = struct(bytes, index, structSizes[i]);
        	index+=structSizes[i];
        }

        // Constants
        // First the sizes
        int[] constSizes = new int[constCnt];
        for(int i=0;i<constCnt;i++){
        	constSizes[i] = NumberConversion.shortFromByteArray(bytes, index);
        	index += 2;
        }
        // Then the values
        this.constants = new String[constCnt];
        for(int i=0;i<constCnt;i++){
        	this.constants[i] = name(bytes, index, constSizes[i]);
        	index+=constSizes[i];
        }
	}
    private String name(byte[] bytes, int index, int size) {
    	String string = new String(bytes, index, size);
    	return string;
    }
    private CodeType codeType(byte[] bytes, int index) {
    	byte type = bytes[index];
    	switch (type) {
		case 0:
			return VoidType.TYPE;
		case 1:
			Short struct = NumberConversion.shortFromByteArray(bytes, index + 1);
			RefType refType = new RefType(struct);
			return refType;
		case 2:
			return BoolType.TYPE;
		case 3:
			return FloatType.TYPE;
		case 4:
			return IntType.TYPE;
		case 5:
			return StringType.TYPE;
		default:
			return null;
		}
	}
    private Struct struct(byte[] bytes, int index, int size) {
        int nameSize = NumberConversion.shortFromByteArray(bytes,  index + 0);
        int varCnt = NumberConversion.shortFromByteArray(bytes,  index + 2);
        index += 4;
        String name = name(bytes, index, nameSize);
		index += nameSize;

        CodeType[] types = new CodeType[varCnt];
        // Types
        for(int i=0;i<varCnt;i++){
        	CodeType type = codeType(bytes, index);
        	types[i] = type;
        	index++;
        	if(type instanceof RefType) index += 2;
        }
        Struct struct = new Struct(name, types);
    	return struct;
    }
    private Procedure procedure(byte[] bytes, int index, int size) {
    	int nameSize = NumberConversion.shortFromByteArray(bytes,  index + 0);
        int parCnt   = NumberConversion.shortFromByteArray(bytes,  index + 2);
		int varCnt   = NumberConversion.shortFromByteArray(bytes,  index + 4);
		int instCnt  = NumberConversion.shortFromByteArray(bytes,  index + 6);

        index += 8;
        String name = name(bytes, index, nameSize);
		index += nameSize;
		CodeType returnType = codeType(bytes, index);
		index++;
    	if(returnType instanceof RefType) index+=2;

		
        // Parameters
        CodeType[] parameters = new CodeType[parCnt];
        for(int i=0;i<parCnt;i++){
        	CodeType type = codeType(bytes, index);
        	parameters[i] = type;
        	index++;
        	if(type instanceof RefType) index+=2;
        }
        // Variables
        CodeType[] variables = new CodeType[varCnt];
        for(int i=0;i<varCnt;i++){
        	CodeType type = codeType(bytes, index);
        	variables[i] = type;
        	index++;
        	if(type instanceof RefType) index+=2;
        }
        // Instructions
        Instruction[] instructions = new Instruction[instCnt];
        int bytecodeStart = index;
        int bytecodeSize = 0;
        for(int i=0;i<instCnt;i++){
        	Instruction instruction = instruction(bytes, index);
        	instructions[i] = instruction;
        	index += instruction.size();
        	bytecodeSize += instruction.size();
        }
        byte[] bytecode = new byte[bytecodeSize];
        copy(bytes, bytecode, bytecodeStart, bytecodeSize);

        Procedure procedure = new Procedure(name, returnType, parameters, variables, instructions, bytecode);
    	return procedure;
    }
	private void copy(byte[] from, byte[] to, int start, int size) {
		for(int i=0;i<size;i++){
			to[i] = from[start+i];
		}
	}
	private Instruction instruction(byte[] bytes, int index) {
    	byte bytecode = bytes[index];
    	Short value, value2;
    	switch (bytecode) {
		case  1:
			return new ADD();
		case  2:
			return new AND();
		case  3:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new CALL((int)value);
		case  4:
			return new EQ();
		case  5:
			return new EXP();
		case  6:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			value2 = NumberConversion.shortFromByteArray(bytes, index + 3);
			return new GETFIELD((int)value, (int)value2);
		case  7:
			return new GT();
		case  8:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new JMP((int)value);
		case  9:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new JMPFALSE((int)value);
		case 10:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new JMPTRUE((int)value);
		case 11:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new LOADGLOBAL((int)value);
		case 12:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new LOADLOCAL((int)value);
		case 13:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			value2 = NumberConversion.shortFromByteArray(bytes, index + 3);
			return new LOADOUTER((int)value, (int)value2);
		case 14:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new NEW((int)value);
		case 15:
			return new NOP();
		case 16:
			return new NOT();
		case 17:
			return new OR();
		case 18:
			byte byteValue = bytes[index + 1];
			return new PUSHBOOL(byteValue == 1);
		case 19:
			float floatValue = NumberConversion.floatFromByteArray(bytes, index + 1);
			return new PUSHFLOAT(floatValue);
		case 20:
			int intValue = NumberConversion.intFromByteArray(bytes, index + 1);
			return new PUSHINT(intValue);
		case 21:
			return new PUSHNULL();
		case 22:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new PUSHSTRING((int)value);
		case 23:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			value2 = NumberConversion.shortFromByteArray(bytes, index + 3);
			return new PUTFIELD((int)value, (int)value2);
		case 24:
			return new RETURN();
		case 25:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new STOREGLOBAL((int)value);
		case 26:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			return new STORELOCAL((int)value);
		case 27:
			value = NumberConversion.shortFromByteArray(bytes, index + 1);
			value2 = NumberConversion.shortFromByteArray(bytes, index + 3);
			return new STOREOUTER((int)value, (int)value2);
		case 28:
			return new POP();
		case 29:
			return new LT();
		case 30:
			return new LTEQ();
		case 31:
			return new GTEQ();
		case 32:
			return new NEQ();
		case 33:
			return new SUB();
		case 34:
			return new MUL();
		case 35:
			return new DIV();
		default:
			return null;
		}
	}
	private static byte[] bytesFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        int offset = 0;
        int readBytes = Integer.MAX_VALUE;
        while(readBytes > 0){
            readBytes = fileInputStream.read(bytes, offset, size-offset);
            offset += readBytes;
        }
        fileInputStream.close();
        return bytes;
    }
	public Variable[] getVariables() {
		return variables;
	}
	public Procedure[] getProcedures() {
		return procedures;
	}
	public Struct[] getStructs() {
		return structs;
	}
	public String[] getConstants() {
		return constants;
	}
	public int getMainNum() {
		return mainNum;
	}
}
