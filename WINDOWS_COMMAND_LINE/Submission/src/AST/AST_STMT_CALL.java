package AST;

import IR.IR_STMT_CALL;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.NullFieldException;
import SemanticAnalysis.SemanticAnalysisException;
import Utils.DebugPrint;

public class AST_STMT_CALL extends AST_STMT 
{
	public AST_CALL call;
	
	public AST_STMT_CALL(AST_CALL call) throws NullFieldException
	{
		if (call == null)
		{
			throw new NullFieldException("AST_STMT_CALL.call");
		}
		
		this.call = call;
	}
	
	/**
	 * @brief 	Validates the call-statement by validating the call.
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	an empty ICTypeInfo if the call-statement is valid, null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// Validating the call
		if (call.validate(className) == null)
		{
			DebugPrint.print("AST_STMT_CALL.validate: The call isn't valid.");
			return null;
		}
		
		// The call-statement is valid
		return new ICTypeInfo();
	}
	
	/**
	 * @brief 	bequeathing the class and function names to the child,
	 * 			after asserting they are initialized.
	 */
	private void bequeathClassAndFunctionNamesToChild() throws ClassOrFunctionNamesNotInitializedExecption
	{
		// Asserting the names are initialized in this node
		assertClassAndFunctionNamesInitialized();
		
		// Bequeathing the names to the call child
		call.currentClassName = this.currentClassName;
		call.currentFunctionName = this.currentFunctionName;
	}
	
	/**
	 * @brief	Creates an IR_STMT_CALL by using the child's created IR_CALL.
	 */
	@Override
	public IR_STMT_CALL createIR() throws SemanticAnalysisException
	{
		bequeathClassAndFunctionNamesToChild();
		return new IR_STMT_CALL(call.createIR());
	}
}
