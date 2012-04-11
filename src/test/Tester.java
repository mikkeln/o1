package test;

import java.io.File;

import compiler.Compiler;

public class Tester {
	private String path = null;
	public Tester(String path) {
		this.path = path;
	}
	public void test() {
		System.out.println("Testing compiler class.");
		File file = new File(this.path);
		if(file.isDirectory()){
			int failed = 0;
			System.out.println("Testing files in " + file.getAbsolutePath());
			File[] files = file.listFiles(new FileEndingFilter("d"));
			for (int i = 0; i < files.length; i++) {
				String filename = files[i].getName();
				boolean shouldFail = filename.contains("fail".subSequence(0, 4));
				String fallPass = shouldFail ? "FAIL" : "PASS";
				System.out.println("Test[" + (i+1) + "] SHOULD " + fallPass + ": " + filename);
				Compiler compiler = new Compiler(this.path + File.separator + filename,
						this.path + File.separator + outFileName(filename, ".ast"),
						this.path + File.separator + outFileName(filename, ".bin"));
				if( ! testCompiler(compiler, shouldFail, i+1))
					failed++;
			}
			if(failed==0)
				System.out.println("Test completed successfully.");
			else
				System.out.println("Some tests failed (" + failed + " / " + files.length + ")");
		} else {
			System.out.println("The path (" + this.path + ") is not a directory.");
		}
	}
	public boolean testCompiler(Compiler compiler, boolean shouldFail, int number) {
		boolean testOk = true;
		try {
			int res = compiler.compile();
			switch(res){
			case 1:
				testOk = false;
				System.out.println("BAD: Test no " + number + " resulted in syntax error!");
				System.out.println("Syntax error: " + compiler.syntaxError);
				break;
			case 2:
				if( ! shouldFail){
					testOk = false;
					System.out.println("BAD: Test no " + number + " failed when it shouldn't!");
					System.out.println("Error: " + compiler.error);
				}
				break;
			default: 
				if (shouldFail){
					testOk = false;
					System.out.println("BAD: Test no " + number + " didn't fail when it should!");
				}
			}
		} catch (Exception e) {
			System.out.println("Test aborted because of error in Compiler: " + e);
			e.printStackTrace();
			System.exit(1);
		}
		return testOk;
	}

	public static String outFileName(String fileName, String ending) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(0, pos) + ending;
	}
	public static void main(String[] args) {
		Tester tester = new Tester(args[0]);
		tester.test();
	}
}
