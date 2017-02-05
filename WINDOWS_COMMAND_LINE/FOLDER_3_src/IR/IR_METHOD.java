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
	
	public static final String EPILOG_LABEL_SUFFIX = "_epilog";
	
	// C'tor
	public IR_METHOD(IR_LABEL label, IR_STMT_LIST body, int frameSize)
	{
		this.label = label;
		this.body = body; // might be null
		this.frameSize = frameSize;
	}
	
	public void printProlog() throws IOException
	{
		StringBuilder printed = new StringBuilder();
		CodeGen_Utils.codeGen_Push(printed, "$ra");
		CodeGen_Utils.codeGen_Push(printed, "$fp");
		printed.append(String.format("mov $fp, $sp%s",AssemblyFilePrinter.NEW_LINE_STRING));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	public void printEpilog() throws IOException
	{
		StringBuilder printed = new StringBuilder();
		printed.append(String.format("mov $sp, $fp%s",AssemblyFilePrinter.NEW_LINE_STRING));
		CodeGen_Utils.codeGen_Pop(printed, "$fp");
		CodeGen_Utils.codeGen_Pop(printed, "$ra");
		printed.append(String.format("jr $ra%s",AssemblyFilePrinter.NEW_LINE_STRING));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	
	public void generateCode() throws IOException
	{
		// TODO: Doesn't the label name already start with 'Label_' prefix?
		AssemblyFilePrinter.getInstance(null).write(String.format("Label_%s:", this.label.name));
		this.printProlog();
		if (body != null)
		{
			body.generateCode();	
		}
		AssemblyFilePrinter.getInstance(null).write(String.format("Label_%s%s:", this.label.name, EPILOG_LABEL_SUFFIX));
		this.printEpilog();
	}
}
