package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SemanticAnalysisException;

public class IR_EXP_CALL extends IR_EXP{
	public IR_CALL call;
	public IR_EXP_CALL(IR_CALL call)
	{
		this.call=call;
	}
	
	@Override
	public CodeGen_Temp generateCode() throws IOException, SemanticAnalysisException
	{
		this.call.generateCode();
		
		CodeGen_Temp resultTemp = TempGenerator.getAndAddNewTemp();
		StringNLBuilder printed = new StringNLBuilder();
		
		// Note: it should work also for void function. $v0 will keep an old and irrelevant value,
		// but we have already validate a well use of the returned value in the semantic analysis.
		printed.appendNL(String.format("mov %s,$v0",resultTemp.getName()));
		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		return resultTemp;
	}
}
