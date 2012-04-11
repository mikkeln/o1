package runtime;

import bytecode.type.FloatType;

public class Heap {
	private Object[] values;
	private int top = 0;
	public Heap(int size){
		this.values = new Object[size];
	}
	public void putfield(short fieldNumber, Struct struct, Reference reference, Object value) {
		if(struct.types[fieldNumber] instanceof FloatType && value instanceof Integer){
			value = new Float(((Integer)value).intValue());
		}
		this.values[reference.getAddress() + fieldNumber] = value;
	}
	public Object allocate(Struct struct) {
		Reference reference = new Reference(top);
		initializeFields(struct);
		top += struct.size();
		return reference;
	}
	public Object getfield(short fieldNumber, Struct struct, Reference reference) {
		return this.values[reference.getAddress() + fieldNumber];
	}
	private void initializeFields(Struct struct) {
		for(int i=0;i<struct.size(); i++){
			this.values[top + i] = struct.types[i].initialValue();
		}
	}
}
