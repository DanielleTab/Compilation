package AST;

import IR.IR_EXP;
import IR.IR_EXP_NEW_CLASS;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_EXP_NEW_CLASS extends AST_EXP{
	public String newExpClassName;
	
	public AST_EXP_NEW_CLASS(String newExpClassName){
		this.newExpClassName = newExpClassName;
	}
	
	public ICTypeInfo validate(String receivedClassName) throws SemanticAnalysisException
	{
		// do we know this className?
		if(SemanticAnalysis.SymbolTable.doesClassExist(newExpClassName))
			return new ICTypeInfo(newExpClassName, 0);
		else
			return null;
	}

	@Override
	public IR_EXP createIR() throws ClassOrFunctionNamesNotInitializedExecption {
		assertClassAndFunctionNamesInitialized();
		return new IR_EXP_NEW_CLASS(this.newExpClassName);
	}

}
