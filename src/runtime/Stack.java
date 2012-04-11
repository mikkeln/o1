package runtime;

public class Stack {
	private Object[] values;
	private int top = 0;
	public Stack(int size){
		this.values = new Object[size];
	}
	public void push(Object object) {
		if(object == null){
			throw new RuntimeException("Serious problem with stack: Found NULL where it should not be!");
		}
		this.values[top++] = object;
	}
	public Object pop() {
		Object value = this.values[top-1];
		this.values[top-1] = null;
		top--;
		return value;
	}
}
