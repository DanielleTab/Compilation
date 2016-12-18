package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_TYPE_CLASS extends AST_TYPE 
{
	public String className;
	
	public AST_TYPE_CLASS(String className)
	{
		this.className = className;
	}
	
	public ICTypeInfo validate(String receivedClassName)
	{
		return new ICTypeInfo(className, 0);
	}
	
}
