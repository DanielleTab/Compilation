package AST;

import IR.IR_EXP_BINOP;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

public class AST_EXP_BINOP extends AST_EXP
{
	public AST_BINOP op;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,AST_BINOP op)
	{
		this.left = left;
		this.right = right;
		this.op = op;
	}
	

	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		ICTypeInfo leftInfo  = left.validate(className);
		ICTypeInfo rightInfo = right.validate(className);
		
		// fail comes from children
		if(leftInfo==null || rightInfo==null)
			return null;
			
		if(op instanceof AST_BINOP_PLUS)
		{
			// two ints
			if(leftInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT) && rightInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT))
				return new ICTypeInfo(ICTypeInfo.IC_TYPE_INT,0);
			
			// two strings
			if(leftInfo.isFlatICType(ICTypeInfo.IC_TYPE_STRING) && rightInfo.isFlatICType(ICTypeInfo.IC_TYPE_STRING))
				return new ICTypeInfo(ICTypeInfo.IC_TYPE_STRING,0);
			
			// illegal
			String debugMessage = String.format("AST_EXP_BINOP.validate: %s + %s is undefined.", leftInfo, rightInfo);
			DebugPrint.print(debugMessage);
			return null;
		}
		else if(op instanceof AST_BINOP_MINUS || op instanceof AST_BINOP_TIMES || op instanceof AST_BINOP_DIVIDE)
		{
			// two ints
			if(leftInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT) && rightInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT))
				return new ICTypeInfo(ICTypeInfo.IC_TYPE_INT,0);
			
			// illegal
			return null;			
		}
		
		// boolean cases:
		
		else if(op instanceof AST_BINOP_GT || op instanceof AST_BINOP_GTE || op instanceof AST_BINOP_LT || op instanceof AST_BINOP_LTE)
		{
			// two ints
			if(leftInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT) && rightInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT))
				return new ICTypeInfo(ICTypeInfo.IC_TYPE_INT,0);
			
			// illegal
			return null;					
		}
		else if(op instanceof AST_BINOP_EQUAL || op instanceof AST_BINOP_NEQUAL)
		{
			// left <= right or right <= left
			if (SymbolTable.validatePredeccessor(rightInfo, leftInfo) ||
				SymbolTable.validatePredeccessor(leftInfo, rightInfo))
			{
				return new ICTypeInfo(ICTypeInfo.IC_TYPE_INT,0);
			}
			
			// illegal
			return null;			
		}
		
		// shouldn't get here
		return null;
	}
	
	// TODO: implement
	public IR_EXP_BINOP createIR()
	{
		return null;
	}
}