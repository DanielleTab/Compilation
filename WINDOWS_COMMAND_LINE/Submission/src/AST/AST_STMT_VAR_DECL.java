package AST;

import IR.IR_Node;
import IR.IR_STMT_STORE;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.NullFieldException;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;
import Utils.DebugPrint;

public class AST_STMT_VAR_DECL extends AST_STMT 
{
	public AST_TYPE varType;
	public String varName;
	public AST_EXP exp;
	public ICTypeInfo varICTypeInfo; // 'validate' sets this, 'createIR' uses it.
	
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
	
	/**
	 * @brief 	bequeathing the class and function names to the given assignment statement,
	 * 			after asserting they are initialized.
	 */
	private void bequeathClassAndFunctionNamesToAssignment(AST_STMT_ASSIGN assignment) throws ClassOrFunctionNamesNotInitializedExecption
	{
		// Asserting the names are initialized in this node
		assertClassAndFunctionNamesInitialized();
		
		// Bequeathing the names to the assignment
		assignment.currentClassName = this.currentClassName;
		assignment.currentFunctionName = this.currentFunctionName;
	}

	/**
	 * @brief	Converts the variable declaration into an assignment.
	 * 			If the declaration doesn't contain an assignment, assigns default zero value.
	 */
	private AST_STMT_ASSIGN convertToAssignment() throws NullFieldException
	{
		AST_LOCATION assignmentLocation = new AST_LOCATION_SIMPLE(varName);
		
		AST_EXP assignmentValue = exp;
		if (assignmentValue == null)
		{
			// The declaration doesn't contain an assignment
			assignmentValue = new AST_EXP_LITERAL(new AST_LITERAL_INTEGER(
					IR_Node.VAR_DEFAULT_INIT_VALUE)); 
		}
		
		return new AST_STMT_ASSIGN(assignmentLocation, assignmentValue);
	}
	
	/**
	 * @brief	Adds the declared variable to the symbol table, after calculating
	 * 			its offset from the frame pointer. Updates the size of function's frame accordingly.
	 * 			At last, converts the statement into an assignment statement and creates its IR node.
	 */
	@Override
	public IR_STMT_STORE createIR() throws SemanticAnalysisException 
	{
		// Adding the variable to the symbol table
		FunctionSymbolInfo functionInfo = getFunctionSymbolInfo();
		int variableOffset = AST_Node.FRAME_OFFSET_OF_FIRST_LOCAL - functionInfo.frameSize;
		VariableSymbolInfo variableInfo = new VariableSymbolInfo(varName, varICTypeInfo, variableOffset, false);
		SymbolTable.insertNewSymbol(variableInfo);
		functionInfo.frameSize += SymbolTable.ADDRESS_SIZE; 
		
		// Converting the variable declaration into an assignment, and creating its IR node
		AST_STMT_ASSIGN assignment = convertToAssignment();
		bequeathClassAndFunctionNamesToAssignment(assignment);
		return assignment.createIR();
	}
}
