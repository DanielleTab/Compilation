package IR;

public class IR_STMT_IF extends IR_STMT_COND{
	
	// TODO: maybe we should add here if counter in order to create label;
	public IR_STMT_IF(IR_EXP cond, IR_STMT body, IR_LABEL label)
	{
		super(cond,body,label);
	}
}
