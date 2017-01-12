package IR;

public class IR_EXP_CALL extends IR_EXP{
	public String functionName;
	public IR_EXP exp;
	public IR_EXP_CALL(String functionName,IR_EXP exp)
	{
		this.functionName=functionName;
		this.exp=exp;
	}
}
