package IR;

import java.io.IOException;
import java.util.List;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.TempGenerator;

public class IR_CALL extends IR_Node
{

	public IR_EXP calledFunctionAddress; // taken from the virtual table
	public IR_EXP callerAddress; // should be passed as first argument
	public IR_EXP_LIST args;
	
	public IR_CALL(IR_EXP calledFunctionAddress, IR_EXP callerAddress, IR_EXP_LIST args)
	{
		this.calledFunctionAddress = calledFunctionAddress;
		this.callerAddress = callerAddress;
		this.args = args;
	}
	
	
	public void generateCode() throws IOException
	{
//		CodeGen_Temp t1 = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp t2 = (CodeGen_Temp) calledFunctionAddress.generateCode();
		CodeGen_Temp t3 = (CodeGen_Temp) callerAddress.generateCode();
		List<CodeGen_Temp> ts = args.generateCodeList();
		for(int i=ts.size();i>=0;i--)
		{
			// push the args in reverse order.
			CodeGen_Utils.codeGen_Push(ts.get(i).getName());
		}
		
		CodeGen_Utils.codeGen_Push(t3.getName());
		AssemblyFilePrinter.getInstance(null).write(String.format("jal %s%s",t2.getName(), AssemblyFilePrinter.NEW_LINE_STRING));
	}
}
