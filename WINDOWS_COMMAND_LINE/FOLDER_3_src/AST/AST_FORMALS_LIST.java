package AST;

import SemanticAnalysis.FunctionNameInFormalsListIsNotInitializedException;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;
import Utils.DebugPrint;

public class AST_FORMALS_LIST extends AST_Node
{
	public ICTypeInfo formalICType;
	public AST_TYPE formal_type; // shouldn't be null
	public String formal_name; // shouldn't be null
	public AST_FORMALS_LIST tail;
	public int formalNumber;
	
	public AST_FORMALS_LIST(AST_TYPE formal_type,String formal_name,AST_FORMALS_LIST tail)
	{
		this.tail=tail;
		this.formal_type=formal_type;
		this.formal_name=formal_name;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		if(this.currentFunctionName==null)
		{
			throw new FunctionNameInFormalsListIsNotInitializedException();
		}
		else
		{
			// Validating the head
			formalICType=this.formal_type.validate(className);
			if (formalICType == null)
			{
				String debugMessage = String.format("AST_FORMALS_LIST.validate: The formal '%s' of the method '%s.%s' has an invalid type.",
						formal_name, className, currentFunctionName);
				DebugPrint.print(debugMessage);
				return null;
			}
			if(SymbolTable.doesSymbolExistInCurrentScope(formal_name))
			{
				String debugMessage = String.format("AST_FORMALS_LIST.validate: The formal '%s' of the method '%s.%s' is redefined.",
						formal_name, className, currentFunctionName);
				DebugPrint.print(debugMessage);
				return null;
			}
			VariableSymbolInfo formalInfo=new VariableSymbolInfo(formal_name, formalICType);
			if(SymbolTable.addFormalToMethod(className,currentFunctionName, formalInfo)==false)
			{
				String debugMessage = String.format("AST_FORMALS_LIST.validate: Failed adding the formal '%s' to the method '%s.%s'.",
						formal_name, className, currentFunctionName);
				DebugPrint.print(debugMessage);
				return null;
			}
			
			SymbolTable.insertNewSymbol(formalInfo);
				
			// Validating the rest of the list
			if (this.tail != null)
			{
				tail.currentFunctionName = this.currentFunctionName;
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
	
	public void createIR() throws SemanticAnalysisException
	{
		assertClassAndFunctionNamesInitialized();
		VariableSymbolInfo formalInfo=new VariableSymbolInfo(formal_name, formalICType,this.formalNumber*SymbolTable.ADDRESS_SIZE, false);
		SymbolTable.addFormalToMethod(currentClassName,currentFunctionName, formalInfo);
		SymbolTable.insertNewSymbol(formalInfo);
		if(this.tail!=null)
		{
			this.tail.currentFunctionName = this.currentFunctionName;
			this.tail.currentClassName = this.currentClassName;
			this.tail.formalNumber=this.formalNumber+SymbolTable.ADDRESS_SIZE;
			this.tail.createIR();
		}
	}
}