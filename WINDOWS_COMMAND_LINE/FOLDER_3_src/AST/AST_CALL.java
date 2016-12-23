package AST;

import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

public class AST_CALL extends AST_Node 
{
	public AST_EXP exp; // might be null
	public String funcName;
	public AST_EXPS_LIST args; // might be null
	
	public AST_CALL(AST_EXP exp, String funcName, AST_EXPS_LIST args)
	{
		//TODO: delete print
		System.out.println("AST_CALL: "+funcName);
		this.exp = exp;
		this.funcName = funcName;
		this.args = args;
	}
	
	/**
	 * @brief 	Assuming there is a calling object, validates it and
	 * 			then checks if it has a member (field or method) with the name as the specified function.
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	the info of the symbol if it exists, or null otherwise.
	 * @throws SemanticAnalysisException 
	 */
	private SymbolInfo getObjectSymbolInfo(String className) throws SemanticAnalysisException
	{
		// Validating the expression is a valid object
		ICTypeInfo expTypeInfo = exp.validate(className);
		if (expTypeInfo == null)
		{
			DebugPrint.print("AST_CALL.getObjectSymbolInfo: The calling expression isn't a valid expression.");
			return null;
		}
		if (!expTypeInfo.isICClass())
		{
			String debugMessage = 
					String.format("AST_CALL.getObjectSymbolInfo: The calling expression isn't an object, exp : %s", 
								  expTypeInfo); 
			DebugPrint.print(debugMessage);
			return null;
		}
		
		// Checking if the object has a member with the function's name.
		SymbolInfo symbolInfo = SymbolTable.searchSymbolInfoInClassAndUp(expTypeInfo.ICType, funcName);
		if (symbolInfo == null)
		{
			String debugMessage = 
					String.format("AST_CALL.getObjectSymbolInfo: The symbol %s doesn't exist in %s", 
							      funcName, expTypeInfo.ICType);
			DebugPrint.print(debugMessage);
			return null;
		}
		
		return symbolInfo;
	}
	
	/**
	 * @brief 	Checks if the function exists.
	 * 			If there's a calling object, validates it first. 
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	the info of the function if it exists, or null otherwise.
	 * @throws SemanticAnalysisException 
	 */
	private FunctionSymbolInfo getFunctionSymbolInfo(String className) throws SemanticAnalysisException
	{
		SymbolInfo functionSymbolInfo = null;
		
		if (exp == null)
		{
			// There isn't a calling object
			functionSymbolInfo = 
					SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className, funcName);
			if (functionSymbolInfo == null)
			{
				DebugPrint.print("AST_CALL.getFunctionSymbolInfo: The symbol " + funcName + " doesn't exist locally or in " + className);
				return null;
			}
		}
		else
		{
			// There is a calling object
			functionSymbolInfo = getObjectSymbolInfo(className);
			if (functionSymbolInfo == null)
			{
				return null;
			}
		}
		
		// Validates the symbol is a function
		if (!(functionSymbolInfo instanceof FunctionSymbolInfo))
		{
			DebugPrint.print("AST_CALL.getFunctionSymbolInfo: " + funcName + " is not a function.");
			return null;
		}
		
		return (FunctionSymbolInfo)functionSymbolInfo;		
	}
	
	// TODO: Implement.
	/**
	 * @brief 	Validates the actual arguments by matching them with the formal arguments. 
	 * 
	 * @param	funcSymbolInfo - info regarding the function, including the type
	 * 			of its formal arguments and its return value.
	 * 
	 * @return 	the ICTypeInfo of the call's return value if the arguments are valid,
	 * 			null otherwise.
	 */
	private ICTypeInfo validateActualArguments(FunctionSymbolInfo funcSymbolInfo)
	{
		// TODO: Delete
		return null;
	}
	
	/**
	 * @brief 	Validates the call by checking if the specified function exists
	 * 			and checking the type of its arguments. 
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	the ICTypeInfo of the call's return value if the call is valid,
	 * 			null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// Checking if the function exists
		FunctionSymbolInfo functionSymbolInfo = getFunctionSymbolInfo(className);
		if (functionSymbolInfo == null)
		{
			return null;
		}
		
		// Validating the arguments
		return validateActualArguments(functionSymbolInfo);
	}
	
}
