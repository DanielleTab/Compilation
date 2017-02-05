package IR;

import java.io.IOException;

import CodeGen.CodeGen_Temp;
import CodeGen.TempGenerator;

/* 
 * This class extends IR_EXP since it calculates a value.
 * Notice that this isn't the AST_EXP.
 * The value is the address itself and not the content.
 * */
public class IR_EXP_LOCATION_SUBSCRIPT extends IR_EXP 
{
	public IR_EXP arrayBase;
	public IR_EXP subscript;
	
	public IR_EXP_LOCATION_SUBSCRIPT(IR_EXP arrayBase, IR_EXP subscript)
	{
		this.arrayBase = arrayBase;
		this.subscript = subscript;
	}
	
	// TODO: Implement
	public CodeGen_Temp generateCode() throws IOException
	{
		CodeGen_Temp arrayBaseTemp = arrayBase.generateCode();
		CodeGen_Temp subscriptTemp = subscript.generateCode();
		CodeGen_Temp arrayLengthTemp = TempGenerator.getAndAddNewTemp();
		
		StringBuilder printed = new StringBuilder();
		//printed.append(//String.format)
		
		// TODO: Delete default value
		return null;
	}
}
