package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SemanticAnalysisException;

public class IR_STMT_IF extends IR_STMT_COND{
	
	public IR_STMT_IF(IR_EXP cond, IR_STMT body, String labelName)
	{
		super(cond, body, labelName);
	}
	
	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Generates code for the if-statement. The generated code
	 * 			branches to the end-label if the condition equals zero,
	 * 			otherwise it executes the body.
	 */
	@Override
	public void generateCode() throws IOException, SemanticAnalysisException 
	{
		CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
		
		startLabel.generateCode();
		
		// Generating code which calculates the condition and then branches
		// to the end if the condition equals zero.
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("li %s,0", zeroTemp.getName()));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		CodeGen_Temp condTemp = cond.generateCode();
		
		printed = new StringNLBuilder();
		printed.appendNL(String.format("beq %s,%s,%s",
									   condTemp.getName(),
									   zeroTemp.getName(),
									   endLabel.name));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		// Generating code for the body if exists
		if (body != null)
		{
			body.generateCode();
		}
		
		endLabel.generateCode();
	}
}
