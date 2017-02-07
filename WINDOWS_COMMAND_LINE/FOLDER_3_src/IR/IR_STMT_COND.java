package IR;

public abstract class IR_STMT_COND extends IR_STMT
{
	// fields
	public IR_EXP cond;
	public IR_STMT body;
	public IR_LABEL startLabel;
	public IR_LABEL endLabel;
	
	// C'tor
	public IR_STMT_COND(IR_EXP cond, IR_STMT body, String labelName)
	{
		this.cond = cond;
		this.body = body;
		this.startLabel = new IR_LABEL(labelName + "_start");
		this.endLabel = new IR_LABEL(labelName + "_end");
	}
}
