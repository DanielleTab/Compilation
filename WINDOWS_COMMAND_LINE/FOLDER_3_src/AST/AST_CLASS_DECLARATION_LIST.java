package AST;

public class AST_CLASS_DECLARATION_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_CLASS_DECLARATION head;
	public AST_CLASS_DECLARATION_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CLASS_DECLARATION_LIST(AST_CLASS_DECLARATION head,AST_CLASS_DECLARATION_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
}

