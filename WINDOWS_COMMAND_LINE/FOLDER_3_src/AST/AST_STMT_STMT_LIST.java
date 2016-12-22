package AST;

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
}
