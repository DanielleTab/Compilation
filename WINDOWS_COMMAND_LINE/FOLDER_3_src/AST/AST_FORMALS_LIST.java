package AST;

import SemanticAnalysis.FunctionNameInFormalsListIsNotInitializedException;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;
import Utils.DebugPrint;

public class AST_FORMALS_LIST extends AST_Node
{
	public String functionName;
	public AST_TYPE formal_type; // shouldn't be null
	public String formal_name; // shouldn't be null
	public AST_FORMALS_LIST tail;
	
	public AST_FORMALS_LIST(AST_TYPE formal_type,String formal_name,AST_FORMALS_LIST tail)
	{
		this.functionName=null;
		/*
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
		*/
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
			// Validating the head
			ICTypeInfo formalICType=this.formal_type.validate(className);
			if (formalICType == null)
			{
				String debugMessage = String.format("AST_FORMALS_LIST.validate: The formal '%s' of the method '%s.%s' has an invalid type.",
						formal_name, className, functionName);
				DebugPrint.print(debugMessage);
				return null;
			}
			if(SymbolTable.doesSymbolExistInCurrentScope(formal_name))
			{
				String debugMessage = String.format("AST_FORMALS_LIST.validate: The formal '%s' of the method '%s.%s' is redefined.",
						formal_name, className, functionName);
				DebugPrint.print(debugMessage);
				return null;
			}
			if(SymbolTable.addFormalToMethod(className,functionName, new VariableSymbolInfo(formal_name, formalICType))==false)
			{
				String debugMessage = String.format("AST_FORMALS_LIST.validate: Failed adding the formal '%s' to the method '%s.%s'.",
						formal_name, className, functionName);
				DebugPrint.print(debugMessage);
				return null;
			}
			
			SymbolTable.insertNewSymbol(new VariableSymbolInfo(formal_name, formalICType));
				
			// Validating the rest of the list
			if (this.tail != null)
			{
				tail.functionName = this.functionName;
				if (tail.validate(className) == null)
				{
					DebugPrint.print("AST_FORMALS_LIST.validate: The tail isn't valid.");
					return null;
				}
			}
		}
		return new ICTypeInfo();
	}
	public boolean isEmpty()
	{
		return ((this.tail==null)&&(this.formal_name==null));
	}
}