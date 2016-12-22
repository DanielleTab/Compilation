package AST;

import SemanticAnalysis.ICTypeInfo;

public abstract class AST_STMT extends AST_Node
{
	public ICTypeInfo expectedReturnType = null;
	public boolean doesAlwaysReturnValue = false;
}