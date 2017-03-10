package AST;
import SemanticAnalysis.ICTypeInfo;

public class AST_BINOP_LTE extends AST_BINOP{
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo();
	}
}