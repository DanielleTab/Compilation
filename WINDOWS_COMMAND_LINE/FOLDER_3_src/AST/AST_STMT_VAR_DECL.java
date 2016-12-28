package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;
import Utils.DebugPrint;

public class AST_STMT_VAR_DECL extends AST_STMT 
{
	public AST_TYPE varType;
	public String varName;
	public AST_EXP exp;
	
	public AST_STMT_VAR_DECL(AST_TYPE varType, String varName)
	{
		this.varType = varType;
		this.varName = varName;
		this.exp = null;
	}
	
	public AST_STMT_VAR_DECL(AST_TYPE varType, String varName, AST_EXP exp)
	{
		this.varType = varType;
		this.varName = varName;
		this.exp = exp;
	}
	
	/**
	 * @brief 	Validates the variable-declaration-statement by validating the type,
	 * 			the expression and the assignment. In addition, checks that the variable
	 * 			name doesn't already exist in the current scope.
	 * 			If the statement is valid, adds the variable to the symbol table.
	 * 
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * 
	 * @return 	an empty ICTypeInfo if the variable-declaration-statement is valid, 
	 * 			null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// Validating the type
		ICTypeInfo varICTypeInfo = varType.validate(className);
		if (varICTypeInfo == null)
		{
			String debugMessage = String.format("AST_STMT_VAR_DECL.validate: The type of the variable '%s' isn't valid.", 
					varName);
			DebugPrint.print(debugMessage);
			return null;
		}
		
		// Making sure that the variable name doesn't already exist in the current scope
		if (SymbolTable.doesSymbolExistInCurrentScope(varName))
		{
			String debugMessage = String.format("AST_STMT_VAR_DECL.validate: The variable '%s' is redefined.", 
					varName);
			DebugPrint.print(debugMessage);
			return null;
		}
		
		if (exp != null)
		{
			// Validating the expression
			ICTypeInfo expressionICTypeInfo = exp.validate(className);
			if (expressionICTypeInfo == null)
			{
				String debugMessage = String.format("AST_STMT_VAR_DECL.validate: The initial expression of the variable '%s' isn't valid.", 
						varName);
				DebugPrint.print(debugMessage);
				return null;
			}
			
			// Checking if the types are compatible for the assignment
			if (!SymbolTable.validatePredeccessor(varICTypeInfo, expressionICTypeInfo))
			{
				String debugMessage = String.format("AST_STMT_VAR_DECL.validate: The assignment can't be done. var : %s, expression : %s", 
						varICTypeInfo, expressionICTypeInfo);
				DebugPrint.print(debugMessage);
				return null;
			}
		}
		
		// Adding the variable to the symbol table
		VariableSymbolInfo varSymbolInfo = new VariableSymbolInfo(varName, varICTypeInfo);
		SymbolTable.insertNewSymbol(varSymbolInfo);
		
		// The variable-declaration-statement is valid
		return new ICTypeInfo();
	}
}
