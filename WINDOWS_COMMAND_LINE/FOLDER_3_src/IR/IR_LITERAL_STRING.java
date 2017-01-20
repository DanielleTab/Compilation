package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringCollector;
import CodeGen.TempGenerator;

public class IR_LITERAL_STRING extends IR_LITERAL{
	
	public String quote;
	public IR_LABEL label;
	public IR_LITERAL_STRING(String quote) 
	{
		this.quote=quote;
		this.label = new IR_LABEL(StringCollector.addStringAndLabelMapping(this.quote));
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		CodeGen_Temp register = TempGenerator.getAndAddNewTemp();
		AssemblyFilePrinter.getInstance(null).write(String.format("la %s,%s%s", 
				register.getName(), 
				this.label.name,
				AssemblyFilePrinter.NEW_LINE_STRING));
		return register;
	}
}
