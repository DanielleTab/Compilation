package AST;

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
}