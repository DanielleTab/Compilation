package AST;

import IR.IR_EXP;
import IR.IR_METHOD;
import IR.IR_STMT_RETURN;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.ExpectedReturnTypeIsNotInitializedException;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

public class AST_STMT_RETURN extends AST_STMT 
{
	public AST_EXP returnedExpression; // might be null
	
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
			String debugMessage = String.format("AST_STMT_RETURN.validateValuedReturn: return mismatch, expected : %s, found : %s.",
					expectedReturnType, expType);
			DebugPrint.print(debugMessage);
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
		else if (expectedReturnType.ICType.equals(ICTypeInfo.IC_TYPE_VOID))
		{
			return validateEmptyReturn();
		}
		else
		{
			return validateValuedReturn(className);
		}
	}
	
	/**
	 * @brief 	Bequeathes the class and function names to the child,
	 * 			after asserting they are initialized.
	 */
	private void bequeathClassAndFunctionNamesToChild() throws ClassOrFunctionNamesNotInitializedExecption
	{
		// Asserting the names are initialized in this node
		assertClassAndFunctionNamesInitialized();
		
		if (returnedExpression != null)
		{
			// Bequeathing the names to the returned expression child
			returnedExpression.currentClassName = this.currentClassName;
			returnedExpression.currentFunctionName = this.currentFunctionName;		
		}
	}
	
	/**
	 * @brief	Creates an IR_STMT_RETURN by using the returned expression created IR node
	 * 			(if exists a returned expression).
	 */
	@Override
	public IR_STMT_RETURN createIR() throws SemanticAnalysisException
	{
		bequeathClassAndFunctionNamesToChild();
		
		IR_EXP returnedExpressionIR = null;
		if (returnedExpression != null)
		{
			returnedExpressionIR = returnedExpression.createIR();	
		}
		
		String methodEpilogLabelName = String.format("Label_%s_%s%s", 
				currentClassName, currentFunctionName, IR_METHOD.EPILOG_LABEL_SUFFIX);
		
		return new IR_STMT_RETURN(returnedExpressionIR, methodEpilogLabelName);
	}
}
