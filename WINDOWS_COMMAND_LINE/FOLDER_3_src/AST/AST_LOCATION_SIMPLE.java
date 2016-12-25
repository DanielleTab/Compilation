package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;

public class AST_LOCATION_SIMPLE extends AST_LOCATION
{
	public String name;
	
	public AST_LOCATION_SIMPLE(String name)
	{
		System.out.println("AST_LOCATION SIMPLE: "+name);
		this.name = name;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		SymbolInfo symbolFound = SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className,name);
		
		// location wasn't found
		if(symbolFound==null)
			return null;
		
		// found
		
		if(symbolFound instanceof VariableSymbolInfo)
		{
		return ((VariableSymbolInfo) symbolFound).variableType;
		}
		else
		{
			return null;
		}
	}
}