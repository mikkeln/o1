package compiler;

import java.io.*;

import bytecode.CodeFile;

import syntaxtree.*;
import oblig1parser.*;

public class Compiler {
    private String inFilename = null;
    private String astFilename = null;
    private String binFilename = null;
    public String syntaxError;
    public String error;
    public Compiler(String inFilename, String astFilename, String binFilename){
        this.inFilename = inFilename;
        this.astFilename = astFilename;
        this.binFilename = binFilename;
    }
    public int compile() throws Exception {
                System.out.println("Starting Compile()");
        InputStream inputStream = null;
        inputStream = new FileInputStream(this.inFilename);
        Lexer lexer = new Lexer(inputStream);
        parser parser = new parser(lexer);

        Program program = null;
      try {
           program = (Program)parser.parse().value;
        } catch (Exception e) {
            // Do something here?
        //throw e; // Or something.
	System.out.println("MORDIIIIIIIIIII");
        }
	String finalres = checkSemantics(program);
        // Check semanics.
	System.out.println("finalres : " + finalres);

        if(finalres != "syntax error" && finalres != "semantic error" && program != null){ // If it is all ok:
		      System.out.println("--- Starting code generation ---");	
        	//writeAST(program);
        	generateCode(program);
		
            return 0;
        } else if (finalres == "syntax error"){ // If there is a SYNTAX ERROR (Should not get that for the tests):
	//System.out.println("finalres : " + finalres);
            return 1;
        } else { // If there is a SEMANTIC ERROR (Should get that for the test with "_fail" in the name):
	//System.out.println("finalres : " + finalres);
            return 2;
        }
    }
 
    private String checkSemantics(Program program) throws Exception {
	
	String res = program.semanticChecker();
	return res;
    }


    private void writeAST(Program program) throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.astFilename));
        bufferedWriter.write(program.printAst());
        bufferedWriter.close();
    }
    private void generateCode(Program program) throws Exception {
        CodeFile codeFile = new CodeFile();
        program.generateCode(codeFile);
        byte[] bytecode = codeFile.getBytecode();
        DataOutputStream stream = new DataOutputStream(new FileOutputStream (this.binFilename));
        stream.write(bytecode);
        stream.close();
    }
    public static void main(String[] args) {
        Compiler compiler = new Compiler(args[0], args[1], args[2]);
        int result;
        try {
            result = compiler.compile();
            if(result == 1){
                System.out.println(compiler.syntaxError);
            } else if(result == 2){
                System.out.println(compiler.error);
            }
            //System.exit(result);
        } catch (Exception e) {
           System.out.println("ERROR: " + e);
            // If unknown error.
          System.exit(3);
        }
    }

    public static String indent(int indent){
        String result = "";
        for(int i=0;i<indent; i++){
            result+=" ";
        }
        return result;
    }
}
