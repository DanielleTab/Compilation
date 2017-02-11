package IR;

import java.io.IOException;

import SemanticAnalysis.SemanticAnalysisException;

public abstract class IR_STMT extends IR_Node 
{
	public abstract void generateCode() throws IOException, SemanticAnalysisException;
}
