package AST;

import IR.BinOperation;
import IR.IR_EXP;
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
	
	private IR_EXP getLocationStartingOffset(VariableSymbolInfo symbolFound)
	{
		if(symbolFound.isField)
		{
			// return the "this" address
			return getThisObjectHeapAddress();
		}
		
		// in the other cases - where the symbol is local - we return $fp
		return new IR_TEMP(TempType.fp);
	
	}
	
	@Override
	public IR_EXP_BINOP createIR() throws ClassIsNotInSymbolTableException, ClassOrFunctionNamesNotInitializedExecption
	{
		assertClassAndFunctionNamesInitialized();
		VariableSymbolInfo symbolFound = (VariableSymbolInfo)SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(this.currentClassName,name);
	
		return new IR_EXP_BINOP(getLocationStartingOffset(symbolFound),new IR_LITERAL_CONST(symbolFound.offset),BinOperation.PLUS);
		
	}
}