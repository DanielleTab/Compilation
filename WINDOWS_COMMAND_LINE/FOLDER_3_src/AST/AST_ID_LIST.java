package AST;
public class AST_ID_LIST extends AST_Node
{
	public String head;
	public AST_ID_LIST tail;
	
	public AST_ID_LIST(String head, AST_ID_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

}