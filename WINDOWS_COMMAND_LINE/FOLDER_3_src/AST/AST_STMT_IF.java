package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import Utils.DebugPrint;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond,AST_STMT body)
	{
		this.cond = cond;
		this.body = body;
	}
	
	/**
	 * @brief 	Validates the 'if' statement by validating its condition and body.
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	an empty ICTypeInfo if the node is valid, null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// Validating the condition
		ICTypeInfo conditionTypeInfo = cond.validate(className);
		if (conditionTypeInfo == null)
		{
			DebugPrint.print("AST_STMT_IF.validate: The condition isn't valid");
			return null;
		}
		if (!conditionTypeInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT))
		{
			DebugPrint.print("AST_STMT_IF.validate: The condition's type isn't int.");
			return null;
		}
		
		// Validating the body
		body.expectedReturnType = expectedReturnType;
		if (body.validate(className) == null)
		{
			DebugPrint.print("AST_STMT_IF.validate: The body isn't valid");
			return null;
		}
		
		// The if-statement doesn't always return a value, regardless of its body,
		// since its condition doesn't necessarily always fulfill
		doesAlwaysReturnValue = false;
		
		// The if-statement is valid
		return new ICTypeInfo();
	}
}