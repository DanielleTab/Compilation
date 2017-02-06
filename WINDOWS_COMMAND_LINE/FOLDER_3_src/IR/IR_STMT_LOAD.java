package IR;

import java.io.IOException;

public class IR_STMT_LOAD extends IR_STMT 
{
	// fields
	public IR_EXP dst; // can be either an address (not IR_EXP_MEM!) or a temp.
	public IR_EXP src;
	
	// C'tor
	public IR_STMT_LOAD(IR_EXP dst, IR_EXP src)
	{
		this.dst = dst;
		this.src = src;
	}

	/**
	 *
	 */
	@Override
	public void generateCode() throws IOException 
	{
		if (dst instanceof IR_TEMP)
		{
			
		}
		// TODO Auto-generated method stub
		
	}
}
