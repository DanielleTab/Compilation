package IR;

public class IR_EXP_BINOP extends IR_EXP {
	public IR_EXP left;
	public IR_EXP right;
	public BinOperation operation;
	
	public IR_EXP_BINOP(IR_EXP left, IR_EXP right, BinOperation operation)
	{
		this.left=left;
		this.right=right;
		this.operation=operation;
	}
}
