package AST;

import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassNameNotInitializedException;
import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;

public class AST_FIELD extends AST_FIELD_OR_METHOD
{
	public String className;
	public AST_TYPE type;
	public String fieldName;
	public AST_ID_LIST idsList;
	public ICTypeInfo icFieldType;
	public AST_FIELD(AST_TYPE type,String fieldName,AST_ID_LIST idsList)
	{
		
		this.type = type;
		this.fieldName=fieldName;
		this.idsList=idsList;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		this.className=className;
		icFieldType=type.validate(className);
		if(icFieldType==null)
		{
			return null;
		}
		
		if(SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className,fieldName) != null)
		{
			// This fieldName already exists in the current class or in its predeccessors. so add new field with the same name is prohibited.
			return null;
		}
		
		VariableSymbolInfo fieldInfo = new VariableSymbolInfo(fieldName, icFieldType);
		SymbolTable.insertNewSymbol(fieldInfo);
		SymbolTable.addFieldToClass(className, fieldInfo);
		
		if((idsList != null)&&(!idsList.isEmpty()))
		{
			this.idsList.type=icFieldType;
			if(this.idsList.validate(className)==null)
			{
				// something in the idsList validation went wrong.
				return null;
			}
		}
		return new ICTypeInfo();
	}
	
	// TODO: Implement.
	public void createIR() throws ClassIsNotInSymbolTableException, ClassNameNotInitializedException
	{
		assertClassNameInitialized();
		ClassSymbolInfo currentClassSymbolInfo=SymbolTable.getClassSymbolInfo(className);
		int fieldOffset=currentClassSymbolInfo.size;
		VariableSymbolInfo fieldInfo = new VariableSymbolInfo(fieldName, icFieldType,fieldOffset);
		SymbolTable.insertNewSymbol(fieldInfo);
		SymbolTable.addFieldToClass(className, fieldInfo);
		this.idsList.createIR();
	}
}