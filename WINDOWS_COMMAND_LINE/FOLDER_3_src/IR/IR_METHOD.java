package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Utils;

public class IR_METHOD extends IR_Node 
{
	// fields
	public IR_LABEL label; // so we can call the method
	public IR_STMT_LIST body;
	public int frameSize;
	
	// C'tor
	public IR_METHOD(IR_LABEL label, IR_STMT_LIST body, int frameSize)
	{
		this.label = label;
		this.body = body;
		this.frameSize = frameSize;
	}
	
	public void printProlog() throws IOException
	{
		StringBuilder printed = new StringBuilder();
		CodeGen_Utils.codeGen_Push("$ra");
		CodeGen_Utils.codeGen_Push("$fp");
		printed.append(String.format("mov $fp, $sp%s",AssemblyFilePrinter.NEW_LINE_STRING));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	public void printEpilog() throws IOException
	{
		// TODO: Doesn't this make the pop instructions be written before the move instruction?
		StringBuilder printed = new StringBuilder();
		printed.append(String.format("mov $sp, $fp%s",AssemblyFilePrinter.NEW_LINE_STRING));
		CodeGen_Utils.codeGen_Pop("$fp");
		CodeGen_Utils.codeGen_Pop("$ra");
		printed.append(String.format("jr $ra%s",AssemblyFilePrinter.NEW_LINE_STRING));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	
	public void generateCode() throws IOException
	{
		AssemblyFilePrinter.getInstance(null).write(String.format("Label_%s:", this.label.name));
		this.printProlog();
		body.generateCode();
		this.printEpilog();
	}
}
