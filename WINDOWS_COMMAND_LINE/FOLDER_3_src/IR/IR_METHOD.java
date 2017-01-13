package IR;

public class IR_METHOD extends IR_Node 
{
	// fields
	public IR_LABEL label; // so we can call the method
	public IR_STMT_LIST body;
	public int frameSize;
	
	// C'tor
	public IR_METHOD(IR_LABEL label, IR_STMT_LIST body, int frameSize)
	{
		this.label = label;
		this.body = body;
		this.frameSize = frameSize;
	}
}
