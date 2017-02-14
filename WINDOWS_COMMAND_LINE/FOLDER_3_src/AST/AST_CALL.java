package AST;

import java.util.List;

import IR.BinOperation;
import IR.IR_CALL;
import IR.IR_EXP;
import IR.IR_EXP_BINOP;
import IR.IR_EXP_LIST;
import IR.IR_EXP_MEM;
import IR.IR_LITERAL_CONST;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.FunctionNotInSymbolTableException;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

public class AST_CALL extends AST_Node 
{
	public AST_EXP caller; // might be null
	public String callerClassName = null; // assigned in validate (if a caller exists)
	public String calledFunctionName;
	public AST_EXPS_LIST args; // might be null
	
	public AST_CALL(AST_EXP exp, String funcName, AST_EXPS_LIST args)
	{
		this.caller = exp;
		this.calledFunctionName = funcName;
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
		ICTypeInfo callerTypeInfo = caller.validate(className);
		if (callerTypeInfo == null)
		{
			DebugPrint.print("AST_CALL.getObjectSymbolInfo: The calling expression isn't a valid expression.");
			return null;
		}
		if (!callerTypeInfo.isICClass())
		{
			String debugMessage = 
					String.format("AST_CALL.getObjectSymbolInfo: The calling expression isn't an object, exp : %s", 
								  callerTypeInfo); 
			DebugPrint.print(debugMessage);
			return null;
		}
		
		// Checking if the object has a member with the function's name.
		callerClassName = callerTypeInfo.ICType;
		SymbolInfo symbolInfo = SymbolTable.searchSymbolInfoInClassAndUp(callerClassName, calledFunctionName);
		if (symbolInfo == null)
		{
			String debugMessage = 
					String.format("AST_CALL.getObjectSymbolInfo: The class '%s' doesn't contain the symbol '%s'.", 
								  callerTypeInfo.ICType, calledFunctionName);
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
		
		if (caller == null)
		{
			// There isn't a calling object
			functionSymbolInfo = 
					SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className, calledFunctionName);
			if (functionSymbolInfo == null)
			{
				DebugPrint.print("AST_CALL.getFunctionSymbolInfo: The symbol '" + calledFunctionName + "' doesn't exist locally or in the class '" + className + "'.");
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
			DebugPrint.print("AST_CALL.getFunctionSymbolInfo: '" + calledFunctionName + "' is not a function.");
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
	
	
	/**
	 * @brief 	bequeathing the class and function names to the children,
	 * 			after asserting they are initialized.
	 */
	private void bequeathClassAndFunctionNamesToChildren() throws ClassOrFunctionNamesNotInitializedExecption
	{
		// Asserting the names are initialized in this node
		assertClassAndFunctionNamesInitialized();
		
		// Bequeathing the names to the caller child
		if (caller != null)
		{
			caller.currentClassName = this.currentClassName;
			caller.currentFunctionName = this.currentFunctionName;
		}
	
		// Bequeathing the names to the arguments child
		if (args != null)
		{
			args.currentClassName = this.currentClassName;
			args.currentFunctionName = this.currentFunctionName;
		}
	}
	
	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Returns the (heap) address of the calling object.
	 * 			If there's no explicit caller, the calling object is 'this' object.
	 */
	private IR_EXP getCallerAddress() throws SemanticAnalysisException
	{
		if (caller == null)
		{
			// no explicit caller - therefore the caller is 'this' object.
			return getThisObjectHeapAddress();
		}
		else
		{
			// the stack-value of the caller is actually its heap-address.
			return caller.createIR();
		}
	}
	
	/**
	 * @brief	Returns the called function's offset in the caller's virtual table.
	 */
	private IR_EXP getFunctionOffsetInCallerVirtualTable() throws FunctionNotInSymbolTableException, ClassIsNotInSymbolTableException
	{
		if (callerClassName == null)
		{
			// no explicit caller - therefore the caller is 'this' object.
			callerClassName = currentClassName;
		}
		
		SymbolInfo calledFunctionInfo = SymbolTable.searchSymbolInfoInClassAndUp(callerClassName, calledFunctionName);
		if ((calledFunctionInfo == null) || (!(calledFunctionInfo instanceof FunctionSymbolInfo)))
		{
			throw new FunctionNotInSymbolTableException(callerClassName, calledFunctionName);
		}
		
		return new IR_LITERAL_CONST(((FunctionSymbolInfo)calledFunctionInfo).offset);
	}
	
	/**
	 * @brief	Returns the called function address, which is the content
	 * 			of the appropriate entry in the virtual table.
	 */
	private IR_EXP getCalledFunctionAddress(IR_EXP callerAddress) throws FunctionNotInSymbolTableException, ClassIsNotInSymbolTableException
	{
		// The virtual table is the first thing inside the object, therefore
		// no offset from callerAddress is needed.
		IR_EXP callerVirtualTableAddress = new IR_EXP_MEM(callerAddress); 
		
		IR_EXP calledFunctionOffset = getFunctionOffsetInCallerVirtualTable();
		IR_EXP virtualTableEntryAddress = new IR_EXP_BINOP(callerVirtualTableAddress, 
				   										   calledFunctionOffset, 
				   										   BinOperation.PLUS,
				   										   false);
		
		IR_EXP virtualTableEntryContent = new IR_EXP_MEM(virtualTableEntryAddress);
		return virtualTableEntryContent;
	}
	
	/**
	 * @brief	Creates an IR_CALL after finding the caller's address 
	 * 			(if there's no explicit caller, 'this' is the caller),
	 * 			and its appropriate virtual function address. 	
	 */
	public IR_CALL createIR() throws SemanticAnalysisException
	{
		bequeathClassAndFunctionNamesToChildren();
		
		/*
		 * It means the special case of printInt.
		 */
		if(calledFunctionName.equals(SymbolTable.PRINTINT_FUNC_SYMBOL_NAME))
		{
			IR_EXP_LIST argumentsIR = null;
			if (args != null)
			{
				argumentsIR = args.createIR();
			}
			
			return new IR_CALL(argumentsIR,true);
		}
		
		IR_EXP callerAddress = getCallerAddress();
		IR_EXP calledFunctionAddress = getCalledFunctionAddress(callerAddress);
		
		IR_EXP_LIST argumentsIR = null;
		if (args != null)
		{
			argumentsIR = args.createIR();
		}
		
		return new IR_CALL(calledFunctionAddress, callerAddress, argumentsIR);
	}
}
