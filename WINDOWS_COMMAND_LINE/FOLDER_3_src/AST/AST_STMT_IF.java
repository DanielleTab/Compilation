package AST;

import IR.IR_EXP;
import IR.IR_STMT;
import IR.IR_STMT_IF;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;

public class AST_STMT_IF extends AST_STMT_COND
{

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond, AST_STMT body)
	{
		super(cond, body);
	}
	
	/**
	 * @brief	Creates an IR_STMT_IF by using its children created IR nodes,
	 * 			and creating a new label from the following format:
	 * 			className_funcName_if_ifIndex.
	 */
	@Override
	public IR_STMT_IF createIR() throws SemanticAnalysisException
	{
		bequeathClassAndFunctionNamesToChildren();
		
		IR_EXP condIR = cond.createIR();
		
		SymbolTable.createNewScope();
		IR_STMT bodyIR = body.createIR();
		SymbolTable.closeCurrentScope();
		
		FunctionSymbolInfo functionInfo = getFunctionSymbolInfo();
		int ifIndex = functionInfo.getNewIfIndex();
		String labelName = String.format("%d_if_%s", ifIndex, getLabelPrefix());
		
		return new IR_STMT_IF(condIR, bodyIR, labelName);
	}
	
}