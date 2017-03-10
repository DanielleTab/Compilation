package AST;

import IR.IR_EXP;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
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
	public IR_EXP createIR() throws SemanticAnalysisException 
	{
		assertClassAndFunctionNamesInitialized();
		this.e.currentFunctionName=this.currentFunctionName;
		this.e.currentClassName=this.currentClassName;
		return this.e.createIR();
	}
}
