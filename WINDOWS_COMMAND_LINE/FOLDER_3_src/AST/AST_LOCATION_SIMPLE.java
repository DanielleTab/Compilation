package AST;

import IR.IR_EXP_MEM;
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
	
	// TODO: Implement by creating IR_EXP_MEM out of
	// IR_EXP_BINOP with the $fp and variable offset.
	@Override
	public IR_EXP_MEM createIR()
	{
		// Do something like:
		// return new IR_EXP_MEM(new IR_EXP_BINOP($fp, variableInfo.offset));
		
		// TODO: Change this default value.
		return null;
	}
}