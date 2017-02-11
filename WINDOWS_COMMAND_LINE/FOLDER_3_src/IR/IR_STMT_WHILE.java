package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SemanticAnalysisException;

public class IR_STMT_WHILE extends IR_STMT_COND
{
	public IR_STMT_WHILE(IR_EXP cond, IR_STMT body, String labelName)
	{
		super(cond, body, labelName);
	}

	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Generates code for the while-statement. The generated code
	 * 			branches to the end-label if the condition equals zero,
	 * 			otherwise it executes the body and jumps back to the start-label.
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
		
		// Generating code for the body and for the jump back to the start-label
		body.generateCode();
		
		printed = new StringNLBuilder();
		printed.appendNL(String.format("j %s", startLabel.name));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		endLabel.generateCode();
	}
}
