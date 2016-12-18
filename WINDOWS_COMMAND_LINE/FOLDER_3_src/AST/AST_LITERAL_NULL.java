package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_LITERAL_NULL extends AST_LITERAL
{
	
	public AST_LITERAL_NULL(){
		
	}
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo();
	}
}
