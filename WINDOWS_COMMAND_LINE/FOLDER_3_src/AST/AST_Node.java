package AST;
import IR.BinOperation;
import IR.IR_EXP;
import IR.IR_EXP_BINOP;
import IR.IR_EXP_MEM;
import IR.IR_LITERAL_CONST;
import IR.IR_TEMP;
import IR.TempType;
import SemanticAnalysis.ClassNameNotInitializedException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class AST_Node 
{
	public int SerialNumber;
	
	// The current class (not relevant to AST_PROGRAM and AST_CLASS_DECLARATION_LIST).
	// It is set in the AST_CLASS_DECLARATION c'tor, and from then it is passed 
	// from parent to child.
	public String currentClassName = null;
	
	// The current function (relevant to AST_METHOD and to nodes inside a method).
	// It is set in the AST_METHOD c'tor, and from the it is passed from parent to child.
	public String currentFunctionName = null; 
	
	public static final int FRAME_OFFSET_OF_FIRST_LOCAL = -4;
	// in offset 0, the caller's fp is located.
	public static final int FRAME_OFFSET_OF_RETURN_ADDRESS = 4;
	public static final int FRAME_OFFSET_OF_THE_THIS_ARGUMENT = 8; 
	public static final int FRAME_OFFSET_OF_FIRST_FORMAL = 12;
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// TODO: delete the implementation. This method should be implemented in each inherited class.
		return null;
	}
	
	public void assertClassNameInitialized() throws ClassNameNotInitializedException
	{
		if(this.currentClassName==null)
		{
			throw new ClassNameNotInitializedException();
		}
	}
	
	public void assertClassAndFunctionNamesInitialized() throws ClassOrFunctionNamesNotInitializedExecption
	{
		if(this.currentClassName==null)
		{
			throw new ClassOrFunctionNamesNotInitializedExecption();
		}
		if(currentFunctionName==null)
		{
			throw new ClassOrFunctionNamesNotInitializedExecption();
		}
	}
	
	/**
	 * @brief	Retrieves the heap-address of 'this' from the stack (the address of 'this'
	 * 			is always the function's first argument). 
	 * 			Should be used only in the context of a function. 
	 */
	public IR_EXP getThisObjectHeapAddress()
	{
		IR_EXP fp = new IR_TEMP(TempType.fp);
		IR_EXP firstArgumentOffset = new IR_LITERAL_CONST(FRAME_OFFSET_OF_THE_THIS_ARGUMENT);
		
		IR_EXP firstArgumentAddress = new IR_EXP_BINOP(fp, firstArgumentOffset, BinOperation.PLUS);
		IR_EXP firstArgumentContent = new IR_EXP_MEM(firstArgumentAddress);
		
		return firstArgumentContent; // the first argument is the address of 'this' object.
	}
	
}