package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_TYPE_STRING extends AST_TYPE
{
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo("string",0);
	}
}
