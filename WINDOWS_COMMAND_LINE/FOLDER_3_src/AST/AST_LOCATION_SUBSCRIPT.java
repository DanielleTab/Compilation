package AST;

import IR.IR_EXP_BINOP;
import IR.IR_EXP_MEM;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;
import Utils.DebugPrint;

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
		if(!subscriptInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT))
		{
			DebugPrint.print("AST_LOCATION_SUBSCRIPT.validate: the array index is not an integer. index : " + subscriptInfo);
			return null;			
		}

		// var must be array
		if(varInfo.pointerDepth<1)
		{
			DebugPrint.print("AST_LOCATION_SUBSCRIPT.validate: the expression is not an array. exp : " + varInfo);
			return null;
		}
			
		
		return new ICTypeInfo(varInfo.ICType,varInfo.pointerDepth -1);
	}
	
	// TODO: Implement
	@Override
	public IR_EXP_BINOP createIR()
	{
		// TODO: Change this default value.
		return null;
	}
	
}