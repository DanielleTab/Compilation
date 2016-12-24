package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;

public class AST_LOCATION_SUBSCRIPT extends AST_LOCATION
{
	public AST_EXP var;
	public AST_EXP subscript;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_LOCATION_SUBSCRIPT(AST_EXP var,AST_EXP subscript)
	{
		this.var = var;
		this.subscript = subscript;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		ICTypeInfo varInfo = var.validate(className);
		ICTypeInfo subscriptInfo = subscript.validate(className);
		
		// TODO: is this compilation error or rum time?
		// index is int
		if(subscriptInfo.ICType==ICTypeInfo.IC_TYPE_INT)
			return null;
		
		return new ICTypeInfo(varInfo.ICType,varInfo.pointerDepth -1);
	}
	
}