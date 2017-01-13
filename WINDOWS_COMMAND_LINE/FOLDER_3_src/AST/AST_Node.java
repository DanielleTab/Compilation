package AST;
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
	
}