package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class AST_TYPE extends AST_Node
{

	public abstract ICTypeInfo validate(String className) throws SemanticAnalysisException;

}