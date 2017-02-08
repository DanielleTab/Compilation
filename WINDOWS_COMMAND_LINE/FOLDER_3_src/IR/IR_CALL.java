package IR;

import java.io.IOException;
import java.util.List;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;

public class IR_CALL extends IR_Node
{

	public IR_EXP callerAddress; // should be passed as first argument
	public IR_EXP calledFunctionAddress; // taken from the virtual table
	public IR_EXP_LIST args;
	
	public IR_CALL(IR_EXP calledFunctionAddress, IR_EXP callerAddress, IR_EXP_LIST args)
	{
		this.calledFunctionAddress = calledFunctionAddress;
		this.callerAddress = callerAddress;
		this.args = args;
	}
	
	/**
	 * @brief	Generates code which calls the function, 
	 * after validating the caller's address isn't null.
	 *  */
	public void generateCode() throws IOException
	{
		CodeGen_Temp callerAddressTemp = (CodeGen_Temp) callerAddress.generateCode();
		CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
		
		// validates the ccallerAddress!=null.
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("li %s,0", zeroTemp.getName()));
		printed.appendNL(String.format("beq %s,%s,%s", 
									   callerAddressTemp.getName(), 
									   zeroTemp.getName(),
									   IR_Node.ERROR_LABEL_NAME));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		CodeGen_Temp functionAddressTemp = (CodeGen_Temp) calledFunctionAddress.generateCode();
		List<CodeGen_Temp> ts = args.generateCodeList();
		
		printed = new StringNLBuilder();
		for(int i=ts.size()-1;i>=0;i--)
		{
			// push the args in reverse order.
			CodeGen_Utils.codeGen_Push(printed, ts.get(i).getName());
		}
		
		CodeGen_Utils.codeGen_Push(printed, callerAddressTemp.getName());
		printed.appendNL(String.format("mov $t0,%s", functionAddressTemp.getName()));
		printed.appendNL(String.format("jalr $t0",functionAddressTemp.getName()));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
}
