package AST;

import IR.IR_STMT;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.FunctionNotInSymbolTableException;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;

public abstract class AST_STMT extends AST_Node
{
	public ICTypeInfo expectedReturnType = null;
	public boolean doesAlwaysReturnValue = false;
	
	// some of the implementations will return null 
	// (like AST_STMT_VAR_DECL in case there's no assign,
	// or AST_STMT_LIST in case it's empty or all of the statements return null)
	public abstract IR_STMT createIR() throws SemanticAnalysisException;
	
	/**
	 * @brief 	Returns the symbol info of the current function.
	 */
	protected FunctionSymbolInfo getFunctionSymbolInfo() throws FunctionNotInSymbolTableException, ClassIsNotInSymbolTableException
	{
		SymbolInfo functionInfo = SymbolTable.searchSymbolInfoInClassAndUp(currentClassName, currentFunctionName);
		if ((functionInfo == null) || (!(functionInfo instanceof FunctionSymbolInfo)))
		{
			throw new FunctionNotInSymbolTableException(currentClassName, currentFunctionName);
		}
		
		return (FunctionSymbolInfo)functionInfo;
	}
}