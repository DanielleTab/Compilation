package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_TYPE_ARRAY extends AST_TYPE
{
	public AST_TYPE type;
	
	
	public AST_TYPE_ARRAY(AST_TYPE type)
	{
		this.type = type;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		ICTypeInfo temp =type.validate(className);
		if(temp!=null)
		{
			return new ICTypeInfo(temp.ICType, temp.pointerDepth+1);
		}
		else
		{
			// something went wrong.
			return null;
		}
	}
}
