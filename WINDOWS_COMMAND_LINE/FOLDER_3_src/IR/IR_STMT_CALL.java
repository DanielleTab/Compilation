package IR;

import java.io.IOException;

import SemanticAnalysis.SemanticAnalysisException;

public class IR_STMT_CALL extends IR_STMT
{
	public IR_CALL call;
	
	public IR_STMT_CALL(IR_CALL call)
	{
		this.call=call;
	}
	
	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Generates code for the statement call by generating code
	 * 			for the call itself.
	 */
	@Override
	public void generateCode() throws IOException, SemanticAnalysisException 
	{
		call.generateCode();
	}
}
