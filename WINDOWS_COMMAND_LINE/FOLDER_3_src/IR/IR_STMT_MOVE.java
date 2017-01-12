package IR;

public class IR_STMT_MOVE extends IR_STMT 
{
	// fields
	public IR_EXP dst;
	public IR_EXP src;
	
	// C'tor
	public IR_STMT_MOVE(IR_EXP_MEM dst, IR_EXP src)
	{
		this.dst = dst;
		this.src = src;
	}
}
