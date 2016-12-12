package AST;

public class AST_FIELD_OR_METHOD_LIST extends AST_Node
{
	public AST_FIELD_OR_METHOD head;
	public AST_FIELD_OR_METHOD_LIST tail;
	
	public AST_FIELD_OR_METHOD_LIST(AST_FIELD_OR_METHOD head, AST_FIELD_OR_METHOD_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
}
