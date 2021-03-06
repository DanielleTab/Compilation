package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.TooManyTempsException;

public class IR_TEMP extends IR_EXP{
	TempType tempType;
	public IR_TEMP(TempType tempType)
	{
		this.tempType=tempType;
	}
	
	public CodeGen_Temp generateCode() throws IOException, TooManyTempsException{
		CodeGen_Temp newTemp = TempGenerator.getAndAddNewTemp();
		StringNLBuilder printed = new StringNLBuilder();
		
		if(tempType==TempType.fp)
		{
			printed.appendNL(String.format("mov %s,$fp",newTemp.getName()));			
		}
	
		AssemblyFilePrinter.getInstance(null).write(printed.toString());

		return newTemp;
	}
}
