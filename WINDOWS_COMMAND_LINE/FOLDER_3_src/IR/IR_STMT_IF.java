package IR;

public class IR_STMT_IF extends IR_STMT_COND{
	
	public IR_STMT_IF(IR_EXP cond, IR_STMT body, IR_LABEL label)
	{
		super(cond,body,label);
	}
}
