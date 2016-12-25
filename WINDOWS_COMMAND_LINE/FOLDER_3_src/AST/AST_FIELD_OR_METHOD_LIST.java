package AST;

public class AST_FIELD_OR_METHOD_LIST extends AST_Node
{
	public AST_FIELD_OR_METHOD head;
	public AST_FIELD_OR_METHOD_LIST tail;
	
	public AST_FIELD_OR_METHOD_LIST(AST_FIELD_OR_METHOD head, AST_FIELD_OR_METHOD_LIST tail)
	{
		AST_FIELD_OR_METHOD_LIST iterator=tail;
		if((iterator!=null)&&(!iterator.isEmpty()))
		{
			this.head=tail.head;
			// iterate up to the end of the list
			while(iterator.tail!=null)
			{
				iterator=iterator.tail;
			}
			iterator.tail=new AST_FIELD_OR_METHOD_LIST(head, null);
			this.tail=tail.tail;
		}
		else
		{
			this.head=head;
			this.tail=null;
		}
	}
	public boolean isEmpty()
	{
		return ((this.tail==null)&&(this.head==null));
	}
	
}
