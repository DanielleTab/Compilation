package AST;

import IR.IR_STMT;
import IR.IR_STMT_MOVE;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.NullFieldException;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

public class AST_STMT_ASSIGN extends AST_STMT
{
	public AST_LOCATION location;
	public AST_EXP expression;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_LOCATION location, AST_EXP exp) throws NullFieldException
	{
		if (location == null)
		{
			throw new NullFieldException("AST_STMT_ASSIGN.location");
		}
		if (exp == null)
		{
			throw new NullFieldException("AST_STMT_ASSIGN.exp");
		}
		
		this.location = location;
		this.expression = exp;
	}
	
	/**
	 * @brief 	Validates the assignment-statement by validating both the location
	 * 			and the expression, and then making sure that their types are compatible
	 * 			for the assignment.
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	an empty ICTypeInfo if the call-statement is valid, null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// Validating the location
		ICTypeInfo locationType = location.validate(className);
		if (locationType == null)
		{
			DebugPrint.print("AST_STMT_ASSIGN.validate: The location isn't valid.");
			return null;
		}
		
		// Validating the expression
		ICTypeInfo expressionType = expression.validate(className);
		if (expressionType == null)
		{
			DebugPrint.print("AST_STMT_ASSIGN.validate: The expression isn't valid.");
			return null;
		}
		
		// Checking if the types are compatible for the assignment
		if (!SymbolTable.validatePredeccessor(locationType, expressionType))
		{
			String debugMessage = String.format("AST_STMT_ASSIGN.validate: The assignment can't be done. location : %s, expression : %s", 
												locationType, expressionType);
			DebugPrint.print(debugMessage);
			return null;
		}
		
		// The assignment-statement is valid
		return new ICTypeInfo();
	}
	
	@Override
	public IR_STMT createIR()
	{
		return createSpecificIR();
	}
	
	// TODO: Implement this using location.createSpecificIR() and expression.createIR().
	public IR_STMT_MOVE createSpecificIR()
	{
		// TODO: Change this default value
		return null;
	}
}