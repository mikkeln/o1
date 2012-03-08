package syntaxtree;
import java.util.List;

public class Program {

    List<ClassDecl> decls;

    public Program(List<ClassDecl> decls) {
        this.decls = decls;
    }

    public String printAst(){
        StringBuilder sb = new StringBuilder();
        for (ClassDecl decl : decls) {
            sb.append(decl.printAst());
            sb.append("\n");
        }
        return sb.toString();
    }
}
