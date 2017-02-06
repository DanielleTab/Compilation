package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;

/**
 * This statement stores the source value in the destination address.
 */
public class IR_STMT_STORE extends IR_STMT 
{
	// fields
	public IR_EXP dstAddress; 
	public IR_EXP srcValue;
	
	// C'tor
	public IR_STMT_STORE(IR_EXP dstAddress, IR_EXP srcValue)
	{
		this.dstAddress = dstAddress;
		this.srcValue = srcValue;
	}

	/**
	 * @brief	Generates code for the store statement, by generating code 
	 * 			which calculates the source value and the destination address and
	 * 			then stores the value in the address. 
	 */
	@Override
	public void generateCode() throws IOException 
	{
		// Generating code for calculating the source value
		CodeGen_Temp srcValueTemp = srcValue.generateCode();
		
		// Generating code for calculating the destination address
		CodeGen_Temp dstAddressTemp = dstAddress.generateCode();
		
		// Generating code for storing the source value in the destination address
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("sw %s,0(%s)", 
									   srcValueTemp.getName(),
									   dstAddressTemp.getName()));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
}
