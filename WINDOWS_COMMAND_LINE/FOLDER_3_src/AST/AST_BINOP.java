package AST;

import SemanticAnalysis.ICTypeInfo;
public abstract class AST_BINOP {
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo();
	}
}
