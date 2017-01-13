package AST;

import IR.IR_EXP;
import IR.IR_LABEL;
import IR.IR_STMT;
import IR.IR_STMT_WHILE;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;

public class AST_STMT_WHILE extends AST_STMT_COND
{

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond, AST_STMT body)
	{
		super(cond, body);
	}

	/**
	 * @brief	Creates an IR_STMT_IF by using its children created IR nodes,
	 * 			and creating a new label from the following format:
	 * 			className_funcName_while_whileIndex.
	 */
	@Override
	public IR_STMT_WHILE createIR() throws SemanticAnalysisException  
	{
		bequeathClassAndFunctionNamesToChildren();
		
		IR_EXP condIR = cond.createIR();
		
		SymbolTable.createNewScope();
		IR_STMT bodyIR = body.createIR();
		
		FunctionSymbolInfo functionInfo = getFunctionSymbolInfo();
		int whileIndex = functionInfo.getNewIfIndex();
		IR_LABEL labelIR = new IR_LABEL(String.format("%s_while_%d", getLabelPrefix(), whileIndex));
		
		return new IR_STMT_WHILE(condIR, bodyIR, labelIR);
	}
	
}