package IR;

import java.io.IOException;

import CodeGen.CodeGen_Temp;
import SemanticAnalysis.IRExpListGenerateCodeException;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class IR_EXP extends IR_Node
{
	 public abstract CodeGen_Temp generateCode() throws IOException, SemanticAnalysisException;
}
