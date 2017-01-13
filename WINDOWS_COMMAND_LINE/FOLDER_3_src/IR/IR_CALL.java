package IR;

public class IR_CALL extends IR_Node
{

	public IR_EXP calledFunctionAddress; // taken from the virtual table
	public IR_EXP callerAddress; // should be passed as first argument
	public IR_EXP_LIST args;
	
	public IR_CALL(IR_EXP calledFunctionAddress, IR_EXP callerAddress, IR_EXP_LIST args)
	{
		this.calledFunctionAddress = calledFunctionAddress;
		this.callerAddress = callerAddress;
		this.args = args;
	}
}
