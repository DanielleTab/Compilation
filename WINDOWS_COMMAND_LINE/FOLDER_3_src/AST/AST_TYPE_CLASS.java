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
		// TODO: Danielle check this
	
		// do we know this className?
		if(SemanticAnalysis.SymbolTable.doesClassExist(receivedClassName))
			return new ICTypeInfo(receivedClassName, 0);
		else
			return null;

	}
	
}
