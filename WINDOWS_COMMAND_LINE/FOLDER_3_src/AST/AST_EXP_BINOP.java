package AST;

import SemanticAnalysis.ICTypeInfo;

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
	

	public ICTypeInfo validate(String className)
	{
		ICTypeInfo leftInfo  = left.validate(className);
		ICTypeInfo rightInfo = right.validate(className);
		
		if(op instanceof AST_BINOP_PLUS)
		{
			// two ints
			if(leftInfo.ICType=="int" && rightInfo.ICType=="int")
				return new ICTypeInfo("int",0);
			
			// two strings
			if(leftInfo.ICType=="string" && rightInfo.ICType=="string")
				return new ICTypeInfo("string",0);
			
			// illegal
			return null;
		}
		else if(op instanceof AST_BINOP_MINUS || op instanceof AST_BINOP_TIMES || op instanceof AST_BINOP_DIVIDE)
		{
			// two ints
			if(leftInfo.ICType=="int" && rightInfo.ICType=="int")
				return new ICTypeInfo("int",0);
			
			// illegal
			return null;			
		}
		
		// boolean cases:
		
		else if(op instanceof AST_BINOP_GT || op instanceof AST_BINOP_GTE || op instanceof AST_BINOP_LT || op instanceof AST_BINOP_LTE)
		{
			// two ints
			if(leftInfo.ICType=="int" && rightInfo.ICType=="int")
				return new ICTypeInfo("int",0);
			
			// illegal
			return null;					
		}
		else if(op instanceof AST_BINOP_EQUAL || op instanceof AST_BINOP_NEQUAL)
		{
			// same type
			if(leftInfo.ICType == rightInfo.ICType)
				return new ICTypeInfo("int",0);
			
			// illegal
			return null;			
		}
		
		// shouldn't get here
		return null;
	}
}