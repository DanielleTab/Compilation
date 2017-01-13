package AST;

import IR.IR_EXP_BINOP;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;

public abstract class AST_LOCATION extends AST_Node
{
	// should return an expression holding the address
	// (for example, $fp + offset), and not the value. 
	public abstract IR_EXP_BINOP createIR() throws ClassOrFunctionNamesNotInitializedExecption, ClassIsNotInSymbolTableException;
}