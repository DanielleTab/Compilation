package AST;

import SemanticAnalysis.TailWithNoHeadException;

public class AST_EXPS_LIST extends AST_Node 
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP head;
	public AST_EXPS_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXPS_LIST(AST_EXP head,AST_EXPS_LIST tail) throws TailWithNoHeadException
	{
		if ((head == null) && (tail != null))
		{
			throw new TailWithNoHeadException();
		}
		
		this.head = head;
		this.tail = tail;
	}
	
}