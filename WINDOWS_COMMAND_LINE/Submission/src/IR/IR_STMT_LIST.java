package IR;

import java.io.IOException;

import SemanticAnalysis.SemanticAnalysisException;

public class IR_STMT_LIST extends IR_STMT 
{
	// fields
	public IR_STMT head; 
	public IR_STMT_LIST tail; // might be null
	
	// C'tor
	public IR_STMT_LIST(IR_STMT head, IR_STMT_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Generates the code for the statement list by generating 
	 * 			code for the head and for the tail (if they exist).
	 */
	@Override
	public void generateCode() throws IOException, SemanticAnalysisException
	{
		if (head != null)
		{
			head.generateCode();
			
			if (tail != null)
			{
				tail.generateCode();		
			}
		}
	}
}
