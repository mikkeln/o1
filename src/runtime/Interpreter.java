package runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bytecode.NumberConversion;
import bytecode.type.FloatType;
import bytecode.type.VoidType;

public class Interpreter {
	public Variable[] variables;
	public Procedure[] procedures;
	public Struct[] structs;
	public String[] constants;
	private int mainNum;
	public Interpreter(Variable[] variables, Procedure[] procedures,
			Struct[] structs, String[] constants, int mainNum) {
		super();
		this.variables = variables;
		this.procedures = procedures;
		this.structs = structs;
		this.constants = constants;
		this.mainNum = mainNum;
	}
	public String list(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Variables:\n");
		for (int i=0; i<this.variables.length; i++) {
			Variable variable = this.variables[i];
			buffer.append("" + i + ": " + variable.toString(this) + "\n");
		}
		buffer.append("Procedures:\n");
		for (int i=0; i<this.procedures.length; i++) {
			Procedure procedure = this.procedures[i];
			buffer.append("" + i + ": " + procedure.toString(this) + "\n");
		}
		buffer.append("Structs:\n");
		for (int i=0; i<this.structs.length; i++) {
			Struct struct = this.structs[i];
			buffer.append("" + i + ": " + struct.toString(this) + "\n");
		}
		buffer.append("Constants:\n");
		for (int i=0; i<this.constants.length; i++) {
			String constant = this.constants[i];
			buffer.append("" + i + ": " + constant + "\n");
		}
		buffer.append("STARTWITH: " + this.procedures[this.mainNum].name);
		buffer.append("\n");
		return buffer.toString();
	}
	public void run(){
		Object[] globals = new Object[this.variables.length];
		Stack stack = new Stack(1000);
		Heap heap = new Heap(1000);
		ActivationBlock current = new ActivationBlock(this.procedures[this.mainNum]);
		initializeParams(current, stack);
		initializeCallVars(current);
		initializeVars(globals);

		int pc = 0;
		while(true){
			switch (current.procedure.bytecode[pc]) {
			case  1:
				// ADD
				stack.push(add(stack.pop(), stack.pop()));
				pc++;
				break;
			case  2:
				// AND
				stack.push(and(stack.pop(), stack.pop()));
				pc++;
				break;
			case  3:
				// CALL
				if(this.procedures[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)].bytecode.length == 0){
					invokeLibProcedure(this.procedures[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)], stack);
					pc+=3;
				} else {
					current.pc = pc + 3;
					ActivationBlock callBlock = new ActivationBlock(this.procedures[NumberConversion.shortFromByteArray(
							current.procedure.bytecode, pc + 1)]);
					initializeParams(callBlock, stack);
					initializeCallVars(callBlock);
					stack.push(current);
					current = callBlock;
					pc = 0;
				}
				break;
			case  4:
				// EQ
				stack.push(eq(stack.pop(), stack.pop()));
				pc++;
				break;
			case  5:
				// EXP
				stack.push(exp(stack.pop(), stack.pop()));
				pc++;
				break;
			case  6:
			    // GETFIELD
				stack.push(getfield(heap, NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1),
						this.structs[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 3)],
						stack.pop()));
				pc+=5;
				break;
			case  7:
			    // GT
				stack.push(gt(stack.pop(), stack.pop()));
				pc++;
				break;
			case  8:
				// JMP
				pc = NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1);
				break;
			case  9:
			    // JMPFALSE
				if(((Boolean)stack.pop()).booleanValue()){
					pc+=3;
				} else {
					pc = NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1);
				}
				break;
			case 10:
			    // JMPTRUE
				if(((Boolean)stack.pop()).booleanValue()){
					pc = NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1);
				} else {
					pc+=3;
				}
				break;
			case 11:
			    // LOADGLOBAL
				stack.push(globals[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)]);
				pc += 3;
				break;
			case 12:
				// LOADLOCAL
				stack.push(current.variables[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)]);
				pc += 3;
				break;
			case 13:
			    // LOADOUTER
			    // TODO: Not implemented in this version.
				throw new RuntimeException("LOADOUTER not implemented");
			case 14:
				// NEW
				stack.push(newStruct(heap, this.structs[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)]));
				pc += 3;
				break;
			case 15:
				// NOP
				pc++;
				break;
			case 16:
				// NOT
				stack.push(not(stack.pop()));
				pc++;
				break;
			case 17:
				// OR
				stack.push(or(stack.pop(), stack.pop()));
				pc++;
				break;
			case 18:
				// PUSHBOOL
				boolean boolValue = (current.procedure.bytecode[pc + 1] == 1);
				stack.push(new Boolean(boolValue));
				pc+=2;
				break;
			case 19:
				// PUSHFLOAT
				float floatValue = NumberConversion.floatFromByteArray(current.procedure.bytecode, pc + 1);
				stack.push(new Float(floatValue));
				pc+=5;
				break;
			case 20:
				// PUSHINT
				int intValue = NumberConversion.intFromByteArray(current.procedure.bytecode, pc + 1);
				stack.push(new Integer(intValue));
				pc+=5;
				break;
			case 21:
				// PUSHNULL
				stack.push(NullReference.NULL);
				pc++;
				break;
			case 22:
				// PUSHSTRING
				String stringValue = this.constants[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)];
				stack.push(stringValue);
				pc+=3;
				break;
			case 23:
				// PUTFIELD
				putfield(heap, NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1),
						this.structs[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 3)],
						stack.pop(), stack.pop());
				pc+=5;
				break;
			case 24:
				// RETURN
				
				if(current.procedure == this.procedures[this.mainNum]) return;
				
				if(current.procedure.returnType instanceof VoidType){
					current = (ActivationBlock) stack.pop();
				} else {
					Object retvalue = stack.pop();
					if(retvalue instanceof Integer && current.procedure.returnType == FloatType.TYPE){
						retvalue = new Float(((Integer)retvalue).floatValue());
					}
					current = (ActivationBlock) stack.pop();
					stack.push(retvalue);
				}
				pc = current.pc;
				break;
			case 25:
				// STOREGLOBAL
				Object globalValue = stack.pop();
				if(this.variables[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)].type instanceof FloatType &&
						globalValue instanceof Integer){
					globalValue = new Float(((Integer)globalValue).intValue());
				}
				globals[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)] = globalValue;
				pc+=3;
				break;
			case 26:
				// STORELOCAL
				Object localValue = stack.pop();
				if(current.variables[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)] instanceof Float &&
						localValue instanceof Integer){
					localValue = new Float(((Integer)localValue).intValue());
				}
				current.variables[NumberConversion.shortFromByteArray(current.procedure.bytecode, pc + 1)] = localValue;
				pc += 3;
				break;
			case 27:
			 	// STOREOUTER
			    // TODO: Not implemented in this version.
				throw new RuntimeException("STOREOUTER not implemented");
			case 28:
				// POP
				stack.pop();
				pc++;
				break;
			case  29:
			    // LT
				stack.push(lt(stack.pop(), stack.pop()));
				pc++;
				break;
			case  30:
			    // LTEQ
				stack.push(lteq(stack.pop(), stack.pop()));
				pc++;
				break;
			case  31:
			    // GTEQ
				stack.push(gteq(stack.pop(), stack.pop()));
				pc++;
				break;
			case  32:
			    // NEQ
				stack.push(neq(stack.pop(), stack.pop()));
				pc++;
				break;
			case  33:
			    // SUB
				stack.push(sub(stack.pop(), stack.pop()));
				pc++;
				break;
			case  34:
			    // MUL
				stack.push(mul(stack.pop(), stack.pop()));
				pc++;
				break;
			case  35:
			    // DIV
				stack.push(div(stack.pop(), stack.pop()));
				pc++;
				break;

			
			default:
				System.out.println("UNKNOWN INSTRUCTION: " + current.procedure.bytecode[pc] + " at " + pc + " in " +
						current.procedure.name + ".");
				return;
			}
		}
	}
	private void initializeVars(Object[] globals) {
		for(int i=0; i<this.variables.length;i++){
			globals[i] = this.variables[i].type.initialValue();
		}
	}
	private void initializeParams(ActivationBlock current, Stack stack) {
		int parSize = current.procedure.parameters.length;
		for(int i=parSize-1; i>=0;i--){
			current.variables[i] = stack.pop();
			if(current.procedure.parameters[i] instanceof FloatType && current.variables[i] instanceof Integer){
				current.variables[i] = new Float(((Integer)current.variables[i]).intValue());
			}
		}
	}
	private void initializeCallVars(ActivationBlock current) {
		int parSize = current.procedure.parameters.length;
		int varSize = current.procedure.variables.length;
		for(int i=0; i<varSize;i++){
			current.variables[parSize + i] = current.procedure.variables[i].initialValue();
		}
	}
	private void invokeLibProcedure(Procedure procedure, Stack stack) {
		try {
			if(procedure.name.equals("readint")){
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String line = in.readLine();
				stack.push(new Integer(line));
			} else if(procedure.name.equals("readfloat")){
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String line = in.readLine();
				stack.push(new Float(line));
			} else if(procedure.name.equals("readchar")){
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String line = in.readLine();
				if(line.length()!=1){
					throw new RuntimeException("readchar can only read line of length 1");
				}
				stack.push(new Integer(line.toCharArray()[0]));
			} else if(procedure.name.equals("readstring")){
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String line = in.readLine();
				stack.push(line.split(" ")[0]);
			} else if(procedure.name.equals("readline")){
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String line = in.readLine();
				stack.push(line);
			} else if (procedure.name.equals("printint")){
				Integer intValue = (Integer) stack.pop();
				System.out.print(intValue);
			} else if(procedure.name.equals("printfloat")){
				Object top = stack.pop();
				Float floatValue;
				if(top instanceof Float){
					floatValue = (Float) top; 
				} else {
					floatValue = new Float(((Integer) top).floatValue());
				}
				System.out.print(floatValue);
			} else if (procedure.name.equals("printstr")){
				String stringValue = (String) stack.pop();
				System.out.print(stringValue);
			} else if (procedure.name.equals("printline")){
				String stringValue = (String) stack.pop();
				System.out.println(stringValue);
			} else {
				throw new RuntimeException("Could not find library procedure " + procedure.name);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not invoke library procedure " + procedure.name + "because of error " +
					e);
		}
	}
	private Object add(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Integer(((Integer)pop1).intValue() + ((Integer)pop0).intValue()); 
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Float(((Integer)pop1).floatValue() + ((Float)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Float(((Float)pop1).floatValue() + ((Integer)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Float(((Float)pop1).floatValue() + ((Float)pop0).floatValue()); 
		} else if(pop1 instanceof String && pop0 instanceof String){
			return (String)pop1 + (String)pop0;
		} else {
			throw new RuntimeException("ADD not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object sub(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Integer(((Integer)pop1).intValue() - ((Integer)pop0).intValue()); 
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Float(((Integer)pop1).floatValue() - ((Float)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Float(((Float)pop1).floatValue() - ((Integer)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Float(((Float)pop1).floatValue() - ((Float)pop0).floatValue()); 
		} else {
			throw new RuntimeException("SUB not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object mul(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Integer(((Integer)pop1).intValue() * ((Integer)pop0).intValue()); 
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Float(((Integer)pop1).floatValue() * ((Float)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Float(((Float)pop1).floatValue() * ((Integer)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Float(((Float)pop1).floatValue() * ((Float)pop0).floatValue()); 
		} else {
			throw new RuntimeException("MUL not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object div(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Integer(((Integer)pop1).intValue() / ((Integer)pop0).intValue()); 
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Float(((Integer)pop1).floatValue() / ((Float)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Float(((Float)pop1).floatValue() / ((Integer)pop0).floatValue()); 
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Float(((Float)pop1).floatValue() / ((Float)pop0).floatValue()); 
		} else {
			throw new RuntimeException("MUL not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object and(Object pop0, Object pop1) {
		if(pop1 instanceof Boolean && pop0 instanceof Boolean){
			return (Boolean) pop1 && (Boolean) pop0;
		} else {
			throw new RuntimeException("AND not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object eq(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Boolean(pop1.equals(pop0));
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Boolean(pop1.equals(pop0));
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Boolean(pop1.equals(pop0));
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Boolean(pop1.equals(pop0));
		} else if(pop1 instanceof String && pop0 instanceof String){
			return new Boolean(pop1.equals(pop0));
		} else if(pop1 instanceof Boolean && pop0 instanceof Boolean){
			return new Boolean(pop1.equals(pop0));
		} else {
			throw new RuntimeException("EQ not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object exp(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Float(Math.pow(((Integer)pop1).doubleValue(), ((Integer)pop0).doubleValue()));
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Float(Math.pow(((Float)pop1).doubleValue(), ((Integer)pop0).doubleValue()));
		} else {
			throw new RuntimeException("EXP not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object gt(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Boolean(((Integer)pop1).intValue() > ((Integer)pop0).intValue());
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Boolean(((Integer)pop1).floatValue() > ((Float)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Boolean(((Float)pop1).floatValue() > ((Integer)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Boolean(((Float)pop1).floatValue() > ((Float)pop0).floatValue());
		} else {
			throw new RuntimeException("GT not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object gteq(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Boolean(((Integer)pop1).intValue() >= ((Integer)pop0).intValue());
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Boolean(((Integer)pop1).floatValue() >= ((Float)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Boolean(((Float)pop1).floatValue() >= ((Integer)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Boolean(((Float)pop1).floatValue() >= ((Float)pop0).floatValue());
		} else {
			throw new RuntimeException("GTEQ not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object lt(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Boolean(((Integer)pop1).intValue() < ((Integer)pop0).intValue());
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Boolean(((Integer)pop1).floatValue() < ((Float)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Boolean(((Float)pop1).floatValue() < ((Integer)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Boolean(((Float)pop1).floatValue() < ((Float)pop0).floatValue());
		} else {
			throw new RuntimeException("LT not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object lteq(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Boolean(((Integer)pop1).intValue() <= ((Integer)pop0).intValue());
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Boolean(((Integer)pop1).floatValue() <= ((Float)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Boolean(((Float)pop1).floatValue() <= ((Integer)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Boolean(((Float)pop1).floatValue() <= ((Float)pop0).floatValue());
		} else {
			throw new RuntimeException("LTEQ not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object neq(Object pop0, Object pop1) {
		if(pop1 instanceof Integer && pop0 instanceof Integer){
			return new Boolean(((Integer)pop1).intValue() != ((Integer)pop0).intValue());
		} else if(pop1 instanceof Integer && pop0 instanceof Float){
			return new Boolean(((Integer)pop1).floatValue() != ((Float)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Integer){
			return new Boolean(((Float)pop1).floatValue() != ((Integer)pop0).floatValue());
		} else if(pop1 instanceof Float && pop0 instanceof Float){
			return new Boolean(((Float)pop1).floatValue() != ((Float)pop0).floatValue());
		} else {
			throw new RuntimeException("NEQ not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object or(Object pop0, Object pop1) {
		if(pop1 instanceof Boolean && pop0 instanceof Boolean){
			return ((Boolean)pop1).booleanValue() || ((Boolean)pop0).booleanValue();
		} else {
			throw new RuntimeException("OR not possible for " + pop1 + " and " + pop0);
		}
	}
	private Object not(Object pop) {
		if(pop instanceof Boolean){
			return new Boolean( ! ((Boolean)pop).booleanValue());
		} else {
			throw new RuntimeException("NOT not possible for " + pop);
		}
	}
	private void putfield(Heap heap, short fieldNumber, Struct struct, Object pop0, Object pop1) {
		if(pop0 instanceof NullReference){
			throw new RuntimeException("Nullpointer at PUTFIELD");
		}
		heap.putfield(fieldNumber, struct, (Reference) pop0, pop1);
	}
	private Object newStruct(Heap heap, Struct struct) {
		return heap.allocate(struct);
	}
	private Object getfield(Heap heap, short fieldNumber, Struct struct, Object pop1) {
		if(pop1 instanceof NullReference){
			throw new RuntimeException("Nullpointer at GETFIELD");
		}
		return heap.getfield(fieldNumber, struct, (Reference) pop1);
	}
}
