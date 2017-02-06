package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;

public class IR_LABEL extends IR_Node 
{
	// fields
	public String name;
	
	// C'tor
	public IR_LABEL(String name)
	{
		 // add the "Label_" because of the simulator lexer.
		this.name = String.format("Label_%s", name);
	}
	
	
	public void generateCode() throws IOException
	{
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("%s:", name));	
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
}
