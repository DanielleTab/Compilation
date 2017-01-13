package AST;

import IR.BinOperation;
import IR.IR_EXP_BINOP;
import IR.IR_EXP_MEM;
import IR.IR_LITERAL_CONST;
import IR.IR_TEMP;
import IR.TempType;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
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
	public IR_EXP_BINOP createIR() throws ClassIsNotInSymbolTableException, ClassOrFunctionNamesNotInitializedExecption
	{
		assertClassAndFunctionNamesInitialized();
		VariableSymbolInfo symbolFound = (VariableSymbolInfo)SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(this.currentClassName,name);
		return new IR_EXP_BINOP(new IR_TEMP(TempType.fp),new IR_LITERAL_CONST(symbolFound.offset),BinOperation.PLUS);
	}
}