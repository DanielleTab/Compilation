package IR;

import java.io.IOException;

public class IR_CLASS_DECL_LIST extends IR_Node {

	// fields
	public IR_CLASS_DECL head;
	public IR_CLASS_DECL_LIST tail;
	
	// C'tor
	public IR_CLASS_DECL_LIST(IR_CLASS_DECL head, IR_CLASS_DECL_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void generateCode() throws IOException
	{
		this.head.generateCode();
		this.tail.generateCode();
	}
	
}
