package AST;

import IR.IR_EXP;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;

public abstract class AST_EXP extends AST_Node
{
	public String functionName;
	public abstract IR_EXP createIR() throws ClassOrFunctionNamesNotInitializedExecption;
}