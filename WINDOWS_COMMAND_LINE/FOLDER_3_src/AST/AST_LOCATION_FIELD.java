package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;

public class AST_LOCATION_FIELD extends AST_LOCATION
{
	public AST_EXP var;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_LOCATION_FIELD(AST_EXP var,String fieldName)
	{
		this.var = var;
		this.fieldName = fieldName;
	}
	
	// TODO: finish this (Ilana)
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		ICTypeInfo varInfo = var.validate(className);// TODO: is it ok?
		if(varInfo==null)
			return null;
		
		SymbolInfo varFound = SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className,fieldName);
		
		// var must be found in current class or in extended classes + var must be a variable
		if(varFound==null  &&  varFound.getSymbolType() != SymbolInfo.SymbolType.SYMBOL_TYPE_VARIABLE)
			return null;
		
		// var must be ICClass
		if(! ((VariableSymbolInfo) varFound).isICClass())
			return null;
		
		// field must be found in the class of var or in extended classes
		String varClass = ((VariableSymbolInfo) varFound).variableType.ICType; // we already know that class name is not a regular class name
		SymbolInfo fieldFound = SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(varClass,fieldName);

		// field must exist + must be a variable
		if(fieldFound==null  &&  fieldFound.getSymbolType() != SymbolInfo.SymbolType.SYMBOL_TYPE_VARIABLE)
			return null;
		
		//everything is good :)
		return ((VariableSymbolInfo) fieldFound).variableType;
	}
}