package IR;

public class IR_CALL extends IR_Node{

	public String calledFunctionName;
	public IR_EXP exp;
	public IR_EXP_LIST args;
	public IR_CALL(String calledFunctionName,IR_EXP exp, IR_EXP_LIST args)
	{
		this.calledFunctionName=calledFunctionName;
		this.exp=exp;
		this.args=args;
	}
}
