package AST;

import IR.IR_EXP;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class AST_LOCATION extends AST_Node
{
	// should return an expression holding the address
	// (for example, $fp + offset), and not the value. 
	public abstract IR_EXP createIR() throws SemanticAnalysisException;
}