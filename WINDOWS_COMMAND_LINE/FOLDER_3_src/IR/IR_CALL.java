package IR;

public class IR_CALL extends IR_Node{

	public String functionName;
	public IR_EXP exp;
	public IR_EXP_LIST args;
	public IR_CALL(String functionName,IR_EXP exp, IR_EXP_LIST args)
	{
		this.functionName=functionName;
		this.exp=exp;
		this.args=args;
	}
}
