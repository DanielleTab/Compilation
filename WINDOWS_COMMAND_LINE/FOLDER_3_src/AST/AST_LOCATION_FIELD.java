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
		// TODO: or should I take the class of var? (and how?)
		ICTypeInfo varInfo = var.validate(className);
		SymbolInfo fieldFound = SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className,fieldName);

		// illegal field
		if(fieldFound==null)
			return null;
		
		// TODO
		return new ICTypeInfo();
	}
}