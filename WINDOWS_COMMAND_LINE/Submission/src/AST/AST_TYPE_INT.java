package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_TYPE_INT extends AST_TYPE 
{
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo(ICTypeInfo.IC_TYPE_INT, 0);
	}
}
