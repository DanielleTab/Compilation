package AST;

import IR.IR_METHOD;
import IR.IR_METHOD_LIST;

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
	
	public IR_METHOD_LIST createIR()
	{
		if(this.head instanceof AST_METHOD)
		{
			IR_METHOD temp=((AST_METHOD) this.head).createIR();
			return new IR_METHOD_LIST(temp,tail.createIR());
		}
		else // if the head is not a method, we want to iterate over the list until the first method.
		{
			AST_FIELD_OR_METHOD_LIST iterator=tail;
			while(iterator.head instanceof AST_FIELD)
			{
				((AST_FIELD)iterator.head).createIR();
				iterator=iterator.tail;
			}
			return new IR_METHOD_LIST(((AST_METHOD) iterator.head).createIR(), iterator.tail.createIR());
		}
	}
}
