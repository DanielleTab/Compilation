package IR;

public class IR_METHOD_LIST extends IR_Node 
{
	// fields
	public IR_METHOD head;
	public IR_METHOD_LIST tail;
	
	// C'tor
	public IR_METHOD_LIST(IR_METHOD head, IR_METHOD_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void generateCode()
	{
		// TODO: Implement.
	}
}
