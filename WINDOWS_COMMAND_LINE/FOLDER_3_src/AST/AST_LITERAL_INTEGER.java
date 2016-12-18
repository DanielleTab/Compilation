package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_LITERAL_INTEGER extends AST_LITERAL 
{
	public int i;
	
	public AST_LITERAL_INTEGER(Integer i){
		this.i = i;
	}
	
	public ICTypeInfo validate(String className)
	{
		return new ICTypeInfo("int",0);
	}
}
