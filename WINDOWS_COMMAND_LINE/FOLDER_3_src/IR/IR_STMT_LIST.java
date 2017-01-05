package IR;

public class IR_STMT_LIST extends IR_Node 
{
	// fields
	public IR_STMT head;
	public IR_STMT_LIST tail;
	
	// C'tor
	public IR_STMT_LIST(IR_STMT head, IR_STMT_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
}
