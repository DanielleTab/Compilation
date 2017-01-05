package AST;

import IR.IR_EXP;
import IR.IR_EXP_MEM;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_EXP_LOCATION extends AST_EXP
{
	public AST_LOCATION location;
	
	public AST_EXP_LOCATION(AST_LOCATION location)
	{
		this.location = location;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		return location.validate(className);
	}
	
	@Override
	public IR_EXP createIR()
	{
		return createSpecificIR();
	}
	
	// This method is needed because IR_STMT_MOVE expects to receive IR_EXP_MEM
	// and not the generic IR_EXP, so when AST_STMT_ASSIGN creates the IR_STMT_MOVE
	// it needs IR_EXP_MEM.
	public IR_EXP_MEM createSpecificIR()
	{
		return location.createIR();
	}
}