package IR;

import java.io.IOException;

import SemanticAnalysis.SemanticAnalysisException;

public class IR_METHOD_LIST extends IR_Node 
{
	// fields
	public IR_METHOD head;		// might be null if the class doesn't have any methods
	public IR_METHOD_LIST tail; // might be null
	
	// C'tor
	public IR_METHOD_LIST(IR_METHOD head, IR_METHOD_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void generateCode() throws IOException, SemanticAnalysisException
	{
		if (head != null)
		{
			head.generateCode();
			if(this.tail!=null)
			{
				tail.generateCode();
			}
		}
	}
}
