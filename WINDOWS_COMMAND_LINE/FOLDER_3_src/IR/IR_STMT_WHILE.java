package IR;

public class IR_STMT_WHILE extends IR_STMT_COND{
	public IR_STMT_WHILE(IR_EXP cond, IR_STMT body, IR_LABEL label)
	{
		super(cond,body,label);
	}

}
