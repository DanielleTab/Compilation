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
		
		if(!varInfo.isICClass())
		{
			return null;
		}
		
		String varClass = varInfo.ICType;
		SymbolInfo fieldFound = SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(varClass,fieldName);

		// field must exist + must be a variable
		if(fieldFound==null  &&  (!(fieldFound instanceof VariableSymbolInfo)))
			return null;
		
		//everything is good :)
		return ((VariableSymbolInfo) fieldFound).variableType;
	}
}