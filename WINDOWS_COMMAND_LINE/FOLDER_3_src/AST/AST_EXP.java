package AST;

import IR.IR_EXP;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class AST_EXP extends AST_Node
{
	public abstract IR_EXP createIR() throws SemanticAnalysisException;
}