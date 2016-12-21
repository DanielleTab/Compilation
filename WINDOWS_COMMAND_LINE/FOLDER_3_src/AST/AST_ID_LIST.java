package AST;


import SemanticAnalysis.ClassIsNotInSymbolTableException;
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
		this.head = head;
		this.tail = tail;
		this.type=null;
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
	
		else
		{
			// we can't insert this new symbol, probably this symbol name is already in the symbol table.
			return null;
		}
		return new ICTypeInfo();
	}
}