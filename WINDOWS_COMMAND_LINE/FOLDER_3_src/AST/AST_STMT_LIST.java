package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import Utils.DebugPrint;

public class AST_STMT_LIST extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_STMT head;
	public AST_STMT_LIST tail;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	/** Copy Constructor **/
	public AST_STMT_LIST(AST_STMT_LIST other)
	{
		this.head = other.head;
		this.tail = other.tail;
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
		// Validating sons
		head.expectedReturnType = this.expectedReturnType;
		tail.expectedReturnType = this.expectedReturnType;
		if ((head.validate(className) == null) ||
			(tail.validate(className) == null))
		{
			DebugPrint.print("AST_STMT_LIST.validate: One of the sons isn't valid");
			return null;
		}
		
		// Checking if always returning a value (to notify whoever called this method)
		this.doesAlwaysReturnValue = head.doesAlwaysReturnValue || tail.doesAlwaysReturnValue;
		
		// This node is valid
		return new ICTypeInfo();
	}
}