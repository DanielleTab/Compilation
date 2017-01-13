package AST;

import IR.IR_STMT;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class AST_STMT extends AST_Node
{
	public ICTypeInfo expectedReturnType = null;
	public boolean doesAlwaysReturnValue = false;
	
	// some of the implementations will return null 
	// (like AST_STMT_VAR_DECL in case there's no assign)
	public abstract IR_STMT createIR() throws SemanticAnalysisException;
}