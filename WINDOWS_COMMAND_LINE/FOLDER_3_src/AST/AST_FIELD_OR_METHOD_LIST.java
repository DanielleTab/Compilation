package AST;

import IR.IR_METHOD;
import IR.IR_METHOD_LIST;
import SemanticAnalysis.SemanticAnalysisException;

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
	
	public IR_METHOD_LIST createIR() throws SemanticAnalysisException
	{
		assertClassNameInitialized();
		
		if(this.tail!=null)
		{
			this.tail.currentClassName = this.currentClassName;
		}
		
		if(this.head instanceof AST_METHOD)
		{
			AST_METHOD astMethod=(AST_METHOD)this.head;
			astMethod.currentClassName = this.currentClassName;
			IR_METHOD temp=astMethod.createIR();
			
			IR_METHOD_LIST tailCreateIRResult =  null;
			
			if(tail!=null)
			{
				tailCreateIRResult = tail.createIR();
			}
				
			return new IR_METHOD_LIST(temp,tailCreateIRResult);
		}
		else // if the head is not a method, we want to iterate over the list until the first method.
		{
			AST_FIELD_OR_METHOD_LIST iterator=tail;
			while((iterator!=null)&&(iterator.head instanceof AST_FIELD))
			{
				AST_FIELD astField = (AST_FIELD)iterator.head;
				astField.currentClassName = this.currentClassName;
				astField.createIR();
				iterator=iterator.tail;
			}
			
			IR_METHOD headCreateIRResult = null;
			IR_METHOD_LIST tailCreateIRResult = null;
			
			if(iterator!=null)
			{
				if(iterator.head!=null)
				{
					headCreateIRResult = ((AST_METHOD) iterator.head).createIR();
					if(iterator.tail!=null)
					{
						tailCreateIRResult =  iterator.tail.createIR();
					}
				}
			}
			
			return new IR_METHOD_LIST(headCreateIRResult, tailCreateIRResult);
			
		}
	}
}
