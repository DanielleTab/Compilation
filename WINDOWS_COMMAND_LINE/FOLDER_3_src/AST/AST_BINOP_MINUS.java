package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_BINOP_MINUS extends AST_BINOP{
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo();
	}
}
