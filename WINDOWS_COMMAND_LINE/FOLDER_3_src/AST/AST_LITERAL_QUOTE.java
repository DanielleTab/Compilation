package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_LITERAL_QUOTE  extends AST_LITERAL
{
	public String str;
	
	public AST_LITERAL_QUOTE(String str){
		this.str = str;
	}
	
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo("string",0);
	}
}
