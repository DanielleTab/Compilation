package AST;

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
	public AST_EXPS_LIST(AST_EXP head,AST_EXPS_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
}