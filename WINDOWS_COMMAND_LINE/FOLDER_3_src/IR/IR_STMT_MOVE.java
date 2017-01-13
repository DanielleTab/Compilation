package IR;

public class IR_STMT_MOVE extends IR_STMT 
{
	// fields
	public IR_EXP dst; // can be either an address (not IR_EXP_MEM!) or a temp.
	public IR_EXP src;
	
	// C'tor
	public IR_STMT_MOVE(IR_EXP dst, IR_EXP src)
	{
		this.dst = dst;
		this.src = src;
	}
}
