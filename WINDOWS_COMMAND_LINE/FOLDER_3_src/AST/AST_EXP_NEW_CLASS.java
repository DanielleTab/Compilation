package AST;

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

}
