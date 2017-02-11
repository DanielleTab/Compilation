package IR;

import java.io.IOException;

import AST.AST_Node;
import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SemanticAnalysisException;

public class IR_METHOD extends IR_Node 
{
	// fields
	public IR_LABEL label; 
	public IR_STMT_LIST body;
	public int frameSize;
	public boolean isMainFunc;
	public boolean isPrintIntFunc = false;
	
	public static final String EPILOG_LABEL_SUFFIX = "_epilog";
	
	// C'tor
	public IR_METHOD(IR_LABEL label, IR_STMT_LIST body, int frameSize, boolean isMainFunc, boolean isPrintIntFunc)
	{
		this.label = label;
		this.body = body; // might be null
		this.frameSize = frameSize;
		this.isMainFunc = isMainFunc;
		this.isPrintIntFunc = isPrintIntFunc;
	}
	
	public void printProlog() throws IOException
	{
		StringNLBuilder printed = new StringNLBuilder();
		CodeGen_Utils.codeGen_Push(printed, "$ra");
		CodeGen_Utils.codeGen_Push(printed, "$fp");
		printed.appendNL("mov $fp, $sp");
		printed.appendNL(String.format("addi $sp,$sp,%d", (this.frameSize)*(-1)));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	public void printEpilog() throws IOException
	{
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL("mov $sp, $fp");
		CodeGen_Utils.codeGen_Pop(printed, "$fp");
		CodeGen_Utils.codeGen_Pop(printed, "$ra");
		printed.appendNL("jr $ra");
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	public void generatePrintIntCode() throws IOException
	{
		StringNLBuilder printed = new StringNLBuilder();

		CodeGen_Temp givenInteger = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp arg1Offset = TempGenerator.getAndAddNewTemp();
		
		// move the i (the first formal) into $a0 
		printed.appendNL(String.format("addi %s,$fp,%d",arg1Offset.getName(), AST_Node.FRAME_OFFSET_OF_FIRST_FORMAL));
		printed.appendNL(String.format("lw %s,0(%s)",givenInteger.getName(),arg1Offset.getName()));
		
		// $a0 = i, $v0 = 1 for syscall print_int
		printed.appendNL(String.format("mov $a0,%s", givenInteger.getName()));
		printed.appendNL("li $v0,1");
		printed.appendNL("syscall");
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	public void generateCode() throws IOException, SemanticAnalysisException
	{
		this.label.generateCode();
		this.printProlog();
		if(isPrintIntFunc)
		{
			generatePrintIntCode();
		}
		else if (body != null)
		{
			body.generateCode();	
		}
		AssemblyFilePrinter.getInstance(null).write(String.format("%s%s:%s", this.label.name, EPILOG_LABEL_SUFFIX, System.lineSeparator()));
		this.printEpilog();
	}
}
