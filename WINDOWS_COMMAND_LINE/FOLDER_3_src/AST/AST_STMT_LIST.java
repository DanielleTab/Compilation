package AST;

import IR.IR_STMT_LIST;
import SemanticAnalysis.ExpectedReturnTypeIsNotInitializedException;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.TailWithNoHeadException;
import Utils.DebugPrint;

public class AST_STMT_LIST extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_STMT head;		// can be null in case of an empty list
	public AST_STMT_LIST tail;	// can be null 
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail)
	{
		AST_STMT_LIST iterator=tail;
		if((iterator!=null)&&(!iterator.isEmpty()))
		{
			this.head=tail.head;
			// iterate up to the end of the list
			while(iterator.tail!=null)
			{
				iterator=iterator.tail;
			}
			iterator.tail=new AST_STMT_LIST(head, null);
			this.tail=tail.tail;
		}
		else
		{
			this.head=head;
			this.tail=null;
		}
	}
	
	/** Copy Constructor **/
	public AST_STMT_LIST(AST_STMT_LIST other)
	{
		this.head = other.head;
		this.tail = other.tail;
	}
	
	public boolean isEmpty()
	{
		return ((this.tail==null)&&(this.head==null));
	}
	
	/** 
	 * @brief	Validates the list, assuming it has no head.
	 */
	private ICTypeInfo validateListWithNoHead() throws TailWithNoHeadException
	{
		if (tail == null)
		{
			// Empty list - nothing to check.
			return new ICTypeInfo();
		}
		else
		{
			throw new TailWithNoHeadException();
		}
	}
	
	/**
	 * @brief	Checks if always returning a value, according to the head 
	 * 			and the tail of the list.
	 * 
	 * @note	Assumes head isn't null.
	 */
	private void markIfAlwaysReturnValue()
	{
		if (tail == null)
		{
			doesAlwaysReturnValue = head.doesAlwaysReturnValue;	
		}
		else
		{
			doesAlwaysReturnValue = head.doesAlwaysReturnValue || tail.doesAlwaysReturnValue;	
		}
	}
	
	/**
	 * @brief 	Validates the node by validating its sons.  
	 * 			In addition, sets the doesAlwaysReturnValue field, according to the sons, 
	 * 			to notify the method who called this method.
	 * @param	className - the name of the IC class which is currently being analyzed.
	 * @return 	an empty ICTypeInfo if the node is valid, null otherwise.
	 */
	@Override
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		if (head == null)
		{
			return validateListWithNoHead();
		}
		
		if (this.expectedReturnType == null)
		{
			throw new ExpectedReturnTypeIsNotInitializedException();
		}
		
		// Validating the head
		head.expectedReturnType = this.expectedReturnType;
		if (head.validate(className) == null)
		{
			DebugPrint.print("AST_STMT_LIST.validate: The head isn't valid.");
			return null;
		}
		
		// Validating the tail if exists
		if (tail != null)
		{
			tail.expectedReturnType = this.expectedReturnType;
			if (tail.validate(className) == null)
			{
				DebugPrint.print("AST_STMT_LIST.validate: The tail isn't valid.");
				return null;
			}
		}
		
		// This node is valid
		markIfAlwaysReturnValue();
		return new ICTypeInfo();
	}
	
	// TODO: Implement this using head.createIR() and tail.createIR()
	public IR_STMT_LIST createIR() throws SemanticAnalysisException
	{
		assertClassAndFunctionNamesInitialized();
		if((head!=null)&&(tail!=null))
		{
			return new IR_STMT_LIST(head.createIR(),tail.createIR());
		}
		else
		{
			return new IR_STMT_LIST(head.createIR(),null);
		}
	}
}