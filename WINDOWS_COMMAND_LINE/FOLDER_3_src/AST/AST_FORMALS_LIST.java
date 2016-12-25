package AST;

import SemanticAnalysis.FunctionNameInFormalsListIsNotInitializedException;
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
		AST_FORMALS_LIST iterator=tail;
		if((iterator!=null)&&(!iterator.isEmpty()))
		{
			this.formal_name=tail.formal_name;
			this.formal_type=tail.formal_type;
			// iterate up to the end of the list
			while(iterator.tail!=null)
			{
				iterator=iterator.tail;
			}
			iterator.tail=new AST_FORMALS_LIST(formal_type, formal_name, null);
			this.tail=tail.tail;
		}
		else
		{
			this.formal_name=formal_name;
			this.formal_type=formal_type;
			this.tail=null;
		}
		
		
		
		
		this.tail=tail;
		this.formal_type=formal_type;
		this.formal_name=formal_name;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		if(this.functionName==null)
		{
			throw new FunctionNameInFormalsListIsNotInitializedException();
		}
		else
		{
			ICTypeInfo formalICType=this.formal_type.validate(className);
			// iterate over the list
			if(formalICType!=null)
			{
				if(SymbolTable.doesSymbolExistInCurrentScope(formal_name))
				{
					return null;
				}
				if(SymbolTable.addFormalToMethod(className,functionName, new VariableSymbolInfo(formal_name, formalICType))==false)
				{
					return null;
				}
				
				SymbolTable.insertNewSymbol(new VariableSymbolInfo(formal_name, formalICType));
				
			}
		}
		return new ICTypeInfo();
	}
	public boolean isEmpty()
	{
		return ((this.tail==null)&&(this.formal_name==null));
	}
}