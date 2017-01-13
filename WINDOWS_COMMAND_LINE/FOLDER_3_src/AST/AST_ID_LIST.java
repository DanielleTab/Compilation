package AST;


import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassNameNotInitializedException;
import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.TypeInAstIdListIsNotInitialized;
import SemanticAnalysis.VariableSymbolInfo;

public class AST_ID_LIST extends AST_Node
{
	public String head;
	public AST_ID_LIST tail;
	public ICTypeInfo type;
	public AST_ID_LIST(String head, AST_ID_LIST tail)
	{
		AST_ID_LIST iterator=tail;
		if((iterator!=null)&&(!iterator.isEmpty()))
		{
			this.head=tail.head;
			this.type=tail.type;
			// iterate up to the end of the list
			while(iterator.tail!=null)
			{
				iterator=iterator.tail;
			}
			iterator.tail=new AST_ID_LIST(head, null);
			this.tail=tail.tail;
		}
		else
		{
			this.head=head;
			this.tail=null;
			this.type=null;
		}
	}
	
	// returns null if one or more of the names in the list are reserved word.
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		if(type==null)
		{
			throw new TypeInAstIdListIsNotInitialized();
		}
		
		VariableSymbolInfo fieldInfo = new VariableSymbolInfo(head, type);
		if(SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className, head)!=null)
		{
			// can't declare variable with the same name again.
			return null;
		}
		SymbolTable.insertNewSymbol(fieldInfo);
		
		SymbolTable.addFieldToClass(className, fieldInfo);
		if(tail!=null)
		{
			this.tail.type=type;
			if(this.tail.validate(className)==null)
			{
				// something in tail validation went wrong
				return null;
			}
		}
	
		return new ICTypeInfo();
	}
	public boolean isEmpty()
	{
		return ((this.tail==null)&&(this.head==null));
	}
	
	public void createIR() throws ClassIsNotInSymbolTableException, ClassNameNotInitializedException
	{
		assertClassNameInitialized();
		ClassSymbolInfo currentClassSymbolInfo=SymbolTable.getClassSymbolInfo(currentClassName);
		int fieldOffset=currentClassSymbolInfo.size;
		VariableSymbolInfo fieldInfo = new VariableSymbolInfo(head, type, fieldOffset, true);
		SymbolTable.insertNewSymbol(fieldInfo);
		SymbolTable.addFieldToClass(currentClassName, fieldInfo);
		if(this.tail!=null)
		{
			this.tail.currentClassName=this.currentClassName;
			this.tail.createIR();
		}
	}
}