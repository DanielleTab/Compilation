package AST;

import IR.IR_EXP_MEM;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
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
	public IR_EXP_MEM createIR() throws ClassOrFunctionNamesNotInitializedExecption, ClassIsNotInSymbolTableException
	{
		return new IR_EXP_MEM(location.createIR());
	}
	
}