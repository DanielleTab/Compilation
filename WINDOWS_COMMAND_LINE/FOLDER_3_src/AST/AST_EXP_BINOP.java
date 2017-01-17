package AST;

import IR.BinOperation;
import IR.IR_EXP;
import IR.IR_EXP_BINOP;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
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
	
	BinOperation getBinOperation()
	{
		if(this.op instanceof AST_BINOP_MINUS)
		{
			return BinOperation.MINUS;
		}
		if(this.op instanceof AST_BINOP_TIMES)
		{
			return BinOperation.TIMES;
		}
		if(this.op instanceof AST_BINOP_PLUS)
		{
			return BinOperation.PLUS;
		}
		if(this.op instanceof AST_BINOP_DIVIDE)
		{
			return BinOperation.DIVIDE;
		}
		if(this.op instanceof AST_BINOP_GT)
		{
			return BinOperation.GT;
		}
		if(this.op instanceof AST_BINOP_GTE)
		{
			return BinOperation.GTE;
		}
		if(this.op instanceof AST_BINOP_LT)
		{
			return BinOperation.LT;
		}
		if(this.op instanceof AST_BINOP_LTE)
		{
			return BinOperation.LTE;
		}
		if(this.op instanceof AST_BINOP_EQUAL)
		{
			return BinOperation.EQUAL;
		}
		if(this.op instanceof AST_BINOP_NEQUAL)
		{
			return BinOperation.NEQUAL;
		}
		
		return null;
	}
	
	
	public IR_EXP_BINOP createIR() throws SemanticAnalysisException
	{
		BinOperation currentOP=getBinOperation();
		assertClassAndFunctionNamesInitialized();
		
		//right and left can't be null at this point because it is a semantic error.
		left.currentClassName=this.currentClassName;
		right.currentClassName=this.currentClassName;
		left.currentFunctionName=this.currentFunctionName;
		right.currentFunctionName=this.currentFunctionName;
		
		IR_EXP leftExp  = left.createIR();
		IR_EXP rightExp = right.createIR();
		return new IR_EXP_BINOP(leftExp,rightExp,currentOP);
	}
}