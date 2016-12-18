package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_BINOP_DIVIDE extends AST_BINOP{
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo();
	}
}
