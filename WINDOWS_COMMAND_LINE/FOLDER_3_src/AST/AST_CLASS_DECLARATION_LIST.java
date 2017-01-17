package AST;

import IR.IR_CLASS_DECL_LIST;
import SemanticAnalysis.SemanticAnalysisException;

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
		AST_CLASS_DECLARATION_LIST iterator=tail;
		if((iterator!=null)&&(!iterator.isEmpty()))
		{
			this.head=tail.head;
			// iterate up to the end of the list
			while(iterator.tail!=null)
			{
				iterator=iterator.tail;
			}
			iterator.tail=new AST_CLASS_DECLARATION_LIST(head, null);
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
	
	public IR_CLASS_DECL_LIST createIR() throws SemanticAnalysisException
	{
		if(tail!=null)
		{
			return new IR_CLASS_DECL_LIST(head.createIR(),tail.createIR());
		}
		return new IR_CLASS_DECL_LIST(head.createIR(),null);
	}
}

