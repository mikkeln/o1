package runtime;

public class VirtualMachine {
	private String binFilename = null;
	private Loader loader = null;
	private Interpreter interpreter = null;
	public VirtualMachine(String binFilename) {
		this.binFilename = binFilename;
	}
	private void load() throws Exception {
		this.loader = new Loader(binFilename);
		this.loader.load();
		this.interpreter = new Interpreter(loader.getVariables(), loader.getProcedures(),
				loader.getStructs(), loader.getConstants(), loader.getMainNum());
	}
	public void run() throws Exception {
		load();
//		System.out.println("START");
		this.interpreter.run();
//		System.out.println("STOP");
	}
	public void list() throws Exception {
		System.out.println("Loading from file: " + this.binFilename);
		load();
		String list = this.interpreter.list();
		System.out.print(list);
	}
	public static void main(String[] args) {
		try {
			if(args.length>1 && args[0].equals("-l")){
				VirtualMachine vm = new VirtualMachine(args[1]);
				vm.list();
			} else {
				VirtualMachine vm = new VirtualMachine(args[0]);
				vm.run();
			}
		} catch (Exception e) {
			System.out.println("VM error: " + e);
			// TODO: Remove this line:
			e.printStackTrace();
		}
	}
}
