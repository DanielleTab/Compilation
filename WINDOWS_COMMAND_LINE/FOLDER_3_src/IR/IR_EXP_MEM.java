package IR;

public class IR_EXP_MEM extends IR_EXP 
{
	// fields
	public IR_EXP address;
	
	// C'tor
	public IR_EXP_MEM(IR_EXP address)
	{
		this.address = address;
	}
	
	//public IR_EXP_MEM(IR_EXP baseAddress, IR_EXP offset)
}
