package AST;

import SemanticAnalysis.ICTypeInfo;

public abstract class AST_TYPE extends AST_Node
{

	public abstract ICTypeInfo validate(String className);

}