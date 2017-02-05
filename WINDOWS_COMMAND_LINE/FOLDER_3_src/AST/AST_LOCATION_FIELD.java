package AST;

import IR.IR_EXP_LOCATION_FIELD;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.VariableSymbolInfo;
import Utils.DebugPrint;

public class AST_LOCATION_FIELD extends AST_LOCATION
{
	public String varClass;
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
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		ICTypeInfo varInfo = var.validate(className);
		if(varInfo==null)
			return null;
		
		if(!varInfo.isICClass())
		{
			String debugMessage = String.format("AST_LOCATION_FIELD.validate: The expression is not an object, so it doesn't have the field '%s'. exp : %s",
					fieldName, varInfo);
			DebugPrint.print(debugMessage);
			return null;
		}
		
		this.varClass = varInfo.ICType;
		SymbolInfo fieldFound = SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(varClass,fieldName);

		// field must exist + must be a variable
		if(fieldFound==null  ||  (!(fieldFound instanceof VariableSymbolInfo)))
		{
			String debugMessage = String.format("AST_LOCATION_FIELD.validate: The class '%s' doesn't have a field '%s'.",
					varInfo, fieldName);
			DebugPrint.print(debugMessage);
			return null;			
		}

		//everything is good :)
		return ((VariableSymbolInfo) fieldFound).variableType;
	}
	
	@Override
	public IR_EXP_LOCATION_FIELD createIR() throws SemanticAnalysisException
	{
		assertClassAndFunctionNamesInitialized();
		VariableSymbolInfo fieldFound = (VariableSymbolInfo)SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(varClass,fieldName);
		this.var.currentClassName=this.currentClassName;
		this.var.currentFunctionName=this.currentFunctionName;
		return new IR_EXP_LOCATION_FIELD(var.createIR(), fieldFound.offset);
	}
}