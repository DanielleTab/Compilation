package AST;

import SemanticAnalysis.ICTypeInfo;

public class AST_EXP_NEW_CLASS extends AST_EXP{
	public String className;
	
	public AST_EXP_NEW_CLASS(String className){
		this.className = className;
	}
	
	public ICTypeInfo validate(String receivedClassName)
	{
		// do we know this className?
		if(doesClassExist(className))
			return new ICTypeInfo(className, 0);
		else
			return null;
	}

}
