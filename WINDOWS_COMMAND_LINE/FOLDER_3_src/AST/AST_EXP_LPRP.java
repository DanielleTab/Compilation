package AST;

import IR.IR_EXP;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_EXP_LPRP extends AST_EXP 
{
	public AST_EXP e;
	
	public AST_EXP_LPRP(AST_EXP e)
	{
		this.e = e;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		return e.validate(className);
	}

	
	@Override
	public IR_EXP createIR() {
		// TODO Auto-generated method stub
		return null;
	}
}
