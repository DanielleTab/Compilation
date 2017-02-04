package AST;

import IR.IR_STMT_LIST;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;

public class AST_STMT_STMT_LIST extends AST_STMT_LIST 
{	
	public AST_STMT_STMT_LIST(AST_STMT_LIST stmtList)
	{
		super(stmtList);
	}
	
	/**
	 * @brief 	Validates the node.  
	 * 			In addition, sets the doesAlwaysReturnValue field, according to the sons, 
	 * 			to notify the method who called this method.
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * @return 	an empty ICTypeInfo if the node is valid, null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		SymbolTable.createNewScope();
		ICTypeInfo result = super.validate(className);
		SymbolTable.closeCurrentScope();
		return result;
	}
	
	/**
	 * @brief	Creates an IR_STMT_LIST by calling the super's implementation.
	 * 
	 * @note	Might return null in case the list is empty, or all of the statements
	 * 			didn't create IR nodes.
	 */
	@Override
	public IR_STMT_LIST createIR() throws SemanticAnalysisException
	{
		SymbolTable.createNewScope();
		IR_STMT_LIST stmtListIR = super.createIR();
		SymbolTable.closeCurrentScope();
		return stmtListIR;
	}
}
