package AST;

import IR.IR_EXP_CALL;
import IR.IR_STMT_CALL;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_EXP_CALL extends AST_EXP 
{
	public AST_CALL call;
	
	public AST_EXP_CALL(AST_CALL call)
	{
		this.call = call;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		return call.validate(className);
	}
	
	// TODO: build IR_EXP_CALL based on the local field call.
	public IR_EXP_CALL createIR() throws SemanticAnalysisException
	{
		return new IR_EXP_CALL(call.createIR());
	}
}
