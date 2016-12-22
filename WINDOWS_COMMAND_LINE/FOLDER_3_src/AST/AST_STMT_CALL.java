package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.NullFieldException;
import SemanticAnalysis.SemanticAnalysisException;
import Utils.DebugPrint;

public class AST_STMT_CALL extends AST_STMT 
{
	public AST_CALL call;
	
	public AST_STMT_CALL(AST_CALL call)
	{
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
		if (call == null)
		{
			throw new NullFieldException("AST_STMT_CALL.call");
		}
		
		// Validating the call
		if (call.validate(className) == null)
		{
			DebugPrint.print("AST_STMT_CALL.validate: The call isn't valid.");
			return null;
		}
		
		// The call-statement is valid
		return new ICTypeInfo();
	}
}
