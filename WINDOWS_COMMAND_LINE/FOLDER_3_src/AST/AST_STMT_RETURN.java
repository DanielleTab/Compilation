package AST;

import SemanticAnalysis.ExpectedReturnTypeIsNotInitializedException;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

public class AST_STMT_RETURN extends AST_STMT 
{
	public AST_EXP returnedExpression;
	
	public AST_STMT_RETURN(AST_EXP exp)
	{
		this.returnedExpression = exp;
	}
	
	/**
	 * @brief 	Validates the empty return-statement by validating it indeed does not
	 * 			return a value.
	 * 
	 * @return 	an empty ICTypeInfo if the return-statement is valid, null otherwise.
	 */
	private ICTypeInfo validateEmptyReturn()
	{
		// Validating that the statement doesn't return a value
		if (returnedExpression != null)
		{
			DebugPrint.print("AST_STMT_RETURN.validateEmptyReturn: The statement returns a value.");
			return null;
		}
		
		// The statement is valid
		return new ICTypeInfo();
	}
	
	/**
	 * @brief 	Validates the valued return-statement by validating it
	 * 			returns a valid expression from the right type.
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	an empty ICTypeInfo if the return-statement is valid, null otherwise.
	 */
	private ICTypeInfo validateValuedReturn(String className) throws SemanticAnalysisException
	{
		// Validating that the statement indeed returns a value
		if (returnedExpression == null)
		{
			DebugPrint.print("AST_STMT_RETURN.validateValuedReturn: The statement doesn't return a value.");
			return null;
		}
		
		// Validating the returned expression
		ICTypeInfo expType = returnedExpression.validate(className);
		if (expType == null)
		{
			DebugPrint.print("AST_STMT_RETURN.validateValuedReturn: The returned expression isn't valid.");
			return null;
		}
		
		// Validating the returned expression's type
		if (!SymbolTable.validatePredeccessor(expectedReturnType, expType))
		{
			DebugPrint.print("AST_STMT_RETURN.validateValuedReturn: The returned expression is not from the right type.");
			return null;
		}
		
		// The statement is valid
		doesAlwaysReturnValue = true;
		return new ICTypeInfo();
	}
	
	/**
	 * @brief 	Validates the return-statement, which can be either an empty
	 * 			return-statement (return;) or a valued return-statement (return exp;). 
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	an empty ICTypeInfo if the return-statement is valid, null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		if (expectedReturnType == null)
		{
			throw new ExpectedReturnTypeIsNotInitializedException();
		}
		else if (expectedReturnType.ICType == ICTypeInfo.IC_TYPE_VOID)
		{
			return validateEmptyReturn();
		}
		else
		{
			return validateValuedReturn(className);
		}
	}
}
