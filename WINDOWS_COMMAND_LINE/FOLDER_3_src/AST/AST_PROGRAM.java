package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;

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
		SymbolTable.createNewScope();
		AST_CLASS_DECLARATION_LIST iterator=l;
		while(iterator!=null)
		{
			if(iterator.head.validate(className)==null)
			{
				return null;
			}
			iterator=l.tail;
		}

		if(!SymbolTable.doesOneMainExistInProgram())
		{
			return null;
		}
		SymbolTable.closeCurrentScope();
		return new ICTypeInfo();
	}
	
}
