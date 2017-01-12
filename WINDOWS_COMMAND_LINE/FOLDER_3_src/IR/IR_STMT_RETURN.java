package IR;

public class IR_STMT_RETURN extends IR_STMT{
	public IR_EXP returnedExpression;
	public IR_STMT_RETURN(IR_EXP returnedExpression)
	{
		this.returnedExpression=returnedExpression;
	}
}
