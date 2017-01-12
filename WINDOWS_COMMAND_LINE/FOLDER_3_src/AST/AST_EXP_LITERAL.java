package AST;

import IR.IR_EXP;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_EXP_LITERAL extends AST_EXP{
	public AST_LITERAL l;
	
	public AST_EXP_LITERAL(AST_LITERAL l)
	{
		this.l=l;
	}


	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		return l.validate(className);
	}
	
	// create IR_LITERAL_CONST or IR_LITERAL_STRING according to the local field.
	public IR_EXP createIR()
	{
		return null;
	}
}
