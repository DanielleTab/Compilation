package AST;

import IR.IR_EXP;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_EXP_NEW_CLASS extends AST_EXP{
	public String className;
	
	public AST_EXP_NEW_CLASS(String className){
		this.className = className;
	}
	
	public ICTypeInfo validate(String receivedClassName) throws SemanticAnalysisException
	{
		// do we know this className?
		if(SemanticAnalysis.SymbolTable.doesClassExist(className))
			return new ICTypeInfo(className, 0);
		else
			return null;
	}

	// TODO: should return IR_RXP_NEW_CLASS
	@Override
	public IR_EXP createIR() {
		// TODO Auto-generated method stub
		return null;
	}

}
