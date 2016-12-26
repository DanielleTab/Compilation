package AST;

import java.util.List;

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
					String.format("AST_CALL.getObjectSymbolInfo: The class '%s' doesn't contain the symbol '%s'.", 
								  expTypeInfo.ICType, funcName);
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
				DebugPrint.print("AST_CALL.getFunctionSymbolInfo: The symbol '" + funcName + "' doesn't exist locally or in the class '" + className + "'.");
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
			DebugPrint.print("AST_CALL.getFunctionSymbolInfo: '" + funcName + "' is not a function.");
			return null;
		}
		
		return (FunctionSymbolInfo)functionSymbolInfo;		
	}
	
	/**
	 * @brief 	Validates a single actual argument by validating the expression in itself,
	 * 			and then matching it with the type of the formal argument.
	 * 
	 * @param	formalArgumentType
	 * @param	actualArgument - an expression which was passed as actual argument.
	 * 
	 * @return 	true if the actual argument is valid, false otherwise.
	 * @throws SemanticAnalysisException 
	 */
	private boolean validateSingleActualArgument(ICTypeInfo formalArgumentType, 
			AST_EXP actualArgument, String className) throws SemanticAnalysisException
	{
		// Validating the expression
		ICTypeInfo actualArgumentType = actualArgument.validate(className);
		if (actualArgumentType == null)
		{
			DebugPrint.print("AST_CALL.validateSingleActualArgument: The expression is invalid.");
			return false;
		}
		
		// Matching types
		if (!SymbolTable.validatePredeccessor(formalArgumentType, actualArgumentType))
		{
			String debugMessage = String.format("AST_CALL.validateSingleActualArgument: Formal/actual type mismatch, formal : %s, actual : %s", 
												formalArgumentType, actualArgumentType);
			DebugPrint.print(debugMessage);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @brief 	Validates the actual arguments by matching them with the formal arguments,
	 * 			recursively. Also makes sure that the number of formal arguments
	 * 			is the same as the number of actual arguments. 
	 * 
	 * @param	formalArgumentTypes - a list which holds the types of the formal arguments.
	 * 			Since this method is recursive, this list might be partial.
	 * @param	actualArguments - a list which holds the expressions that were passed
	 * 			as actual arguments. This list might be partial as well.
	 * 
	 * @return 	true if the actual arguments are valid, false otherwise.
	 * @throws SemanticAnalysisException 
	 */
	private boolean validateActualArguments(List<ICTypeInfo> formalArgumentTypes, 
			AST_EXPS_LIST actualArguments, String className) throws SemanticAnalysisException
	{
		// Base case - at least one of the lists is empty 
		if ((formalArgumentTypes == null) || (formalArgumentTypes.size() == 0))
		{
			if ((actualArguments == null) || (actualArguments.head == null))
			{
				// All of the arguments were successfully validated and the number of 
				// formal arguments is the same as the one of actual arguments
				return true;
			}
			
			DebugPrint.print("AST_CALL.validateActualArguments: Too many actual arguments.");
			return false;
		}
		if ((actualArguments == null) || (actualArguments.head == null))
		{
			String debugMessage = String.format("AST_CALL.validateActualArguments: Missing %d actual argument(s).",
												formalArgumentTypes.size());
			DebugPrint.print(debugMessage);
			return false;
		}
		
		// Validating the first argument
		if (!validateSingleActualArgument(formalArgumentTypes.get(0), actualArguments.head, className))
		{
			return false;
		}
		
		// Recursively validating the rest of the arguments
		List<ICTypeInfo> formalArgumentsTail = formalArgumentTypes.subList(1, formalArgumentTypes.size());
		return validateActualArguments(formalArgumentsTail, actualArguments.tail, className);
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
		if (!validateActualArguments(functionSymbolInfo.argumentsTypes, args, className))
		{
			return null;
		}
		return functionSymbolInfo.returnType;
	}
	
}
