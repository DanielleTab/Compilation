package AST;

import SemanticAnalysis.FunctionNameInFormalsListIsNotInitialized;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;

public class AST_FORMALS_LIST extends AST_Node
{
	public String functionName;
	public AST_TYPE formal_type;
	public String formal_name;
	public AST_FORMALS_LIST tail;
	
	public AST_FORMALS_LIST(AST_TYPE formal_type,String formal_name,AST_FORMALS_LIST tail)
	{
		this.functionName=null;
		this.tail=tail;
		this.formal_type=formal_type;
		this.formal_name=formal_name;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		if(this.functionName==null)
		{
			throw new FunctionNameInFormalsListIsNotInitialized();
		}
		else
		{
			ICTypeInfo formalICType=this.formal_type.validate(className);
			// iterate over the list
			if(formalICType!=null)
			{
				if(SymbolTable.addFormalToMethod(functionName, new VariableSymbolInfo(formal_name, formalICType))==false)
				{
					return null;
				}
				if(SymbolTable.insertNewSymbol(new VariableSymbolInfo(formal_name, formalICType)) == false)
				{
					return null;
				}
			}
		}
		return new ICTypeInfo();
	}
}