package AST;

import IR.IR_EXP;
import IR.IR_EXP_NEW_ARRAY;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
//import SemanticAnalysis;

public class AST_EXP_NEW_ARRAY extends AST_EXP{
	public AST_TYPE type;
	public AST_EXP size;
	
	public AST_EXP_NEW_ARRAY(AST_TYPE type, AST_EXP size){
		this.type = type;
		this.size = size;
	}
	
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		ICTypeInfo typeInfo = type.validate(className);
		ICTypeInfo sizeInfo = size.validate(className);
		
		// size of array must be int
		if(!sizeInfo.isFlatICType(ICTypeInfo.IC_TYPE_INT))
			return null;
		
		// type must be defined (int / string / defined class name) - we check it in type.validate;
		if(typeInfo == null)
			return null;
		
		// everything is ok
		return new ICTypeInfo(typeInfo.ICType,typeInfo.pointerDepth+1);
	}


	// TODO: should return IR_EXP_NEW_ARRAY
	@Override
	public IR_EXP createIR() throws ClassOrFunctionNamesNotInitializedExecption 
	{
		assertClassAndFunctionNamesInitialized(this.functionName);
		this.size.functionName=this.functionName;
		this.size.className=this.className;
		return new IR_EXP_NEW_ARRAY(this.size.createIR());
	}
	
	
}
