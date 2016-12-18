package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_PROGRAM extends AST_Node 
{
	public AST_CLASS_DECLARATION_LIST l;
	public AST_PROGRAM(AST_CLASS_DECLARATION_LIST l)
	{
		this.l=l;
	}
	
	
	// returns ICTypeInfo = null if the program is not validated
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		AST_CLASS_DECLARATION_LIST iterator=l;
		while(iterator!=null)
		{
			if(iterator.head.validate(className)==null)
			{
				return null;
			}
			iterator=l.tail;
		}
		
		return new ICTypeInfo();
	}
}
