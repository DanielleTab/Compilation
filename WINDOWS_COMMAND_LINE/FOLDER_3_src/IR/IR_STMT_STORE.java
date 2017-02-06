package IR;

import java.io.IOException;

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
	 *
	 */
	@Override
	public void generateCode() throws IOException 
	{
		
		// TODO Auto-generated method stub
		
	}
}
