package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_TYPE_CLASS extends AST_TYPE 
{
	public String typeClassName;
	
	public AST_TYPE_CLASS(String typeClassName)
	{
		this.typeClassName = typeClassName;
	}
	
	public ICTypeInfo validate(String receivedClassName) throws SemanticAnalysisException
	{
		if(SemanticAnalysis.SymbolTable.doesClassExist(typeClassName))
			return new ICTypeInfo(typeClassName, 0);
		else
			return null;

	}
	
}
