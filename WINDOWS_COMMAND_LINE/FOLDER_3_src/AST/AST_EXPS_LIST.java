package AST;

import IR.IR_EXP_LIST;
import SemanticAnalysis.ClassIsNotInSymbolTableException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
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
		AST_EXPS_LIST iterator=tail;
		if((iterator!=null)&&(!iterator.isEmpty()))
		{
			this.head=tail.head;
			// iterate up to the end of the list
			while(iterator.tail!=null)
			{
				iterator=iterator.tail;
			}
			iterator.tail=new AST_EXPS_LIST(head, null);
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
	
	public IR_EXP_LIST createIR() throws ClassOrFunctionNamesNotInitializedExecption, ClassIsNotInSymbolTableException
	{
		assertClassAndFunctionNamesInitialized();
		this.head.currentClassName=this.currentClassName;
		this.head.currentFunctionName=this.currentFunctionName;
		IR_EXP_LIST temp=null;
		if(this.tail!=null)
		{
			this.tail.currentClassName=this.currentClassName;
			this.tail.currentFunctionName=this.currentFunctionName;
			temp=this.tail.createIR();
		}
		return new IR_EXP_LIST(this.head.createIR(),temp);
	}
}