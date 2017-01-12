package IR;

public class IR_STMT_CALL extends IR_STMT{
	public String functionName;
	public IR_EXP exp;
	public IR_STMT_CALL(String functionName,IR_EXP exp)
	{
		this.functionName=functionName;
		this.exp=exp;
	}
}
