package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_TYPE_CLASS extends AST_TYPE 
{
	public String className;
	
	public AST_TYPE_CLASS(String className)
	{
		this.className = className;
	}
	
	public ICTypeInfo validate(String receivedClassName) throws SemanticAnalysisException
	{
		if(SemanticAnalysis.SymbolTable.doesClassExist(className))
			return new ICTypeInfo(className, 0);
		else
			return null;

	}
	
}
