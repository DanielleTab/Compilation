package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;

public class AST_FIELD extends AST_FIELD_OR_METHOD
{
	public AST_TYPE type;
	public String fieldName;
	public AST_ID_LIST idsList;
	
	public AST_FIELD(AST_TYPE type,String fieldName,AST_ID_LIST idsList)
	{
		
		this.type = type;
		this.fieldName=fieldName;
		this.idsList=idsList;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		ICTypeInfo fieldType=type.validate(className);
		if(fieldType==null)
		{
			return null;
		}
		
		if(SymbolTable.searchSymbolInfoInClass(className,fieldName) != null)
		{
			// This fieldName already exists in the current class or in its predeccessors. so add new field with the same name is prohibited.
			return null;
		}
		
		VariableSymbolInfo fieldInfo = new VariableSymbolInfo(fieldName, fieldType);
		if(SymbolTable.insertNewSymbol(fieldInfo))
		{
			SymbolTable.addFieldToClass(className, fieldInfo);
		}
		else
		{			
			// we can't insert this new symbol, probably this symbol name is already in the symbol table.
			return null;
		}
		
		if(idsList != null)
		{
			this.idsList.type=fieldType;
			if(this.idsList.validate(className)==null)
			{
				// something in the idsList validation went wrong.
				return null;
			}
		}
		return new ICTypeInfo();
	}
}