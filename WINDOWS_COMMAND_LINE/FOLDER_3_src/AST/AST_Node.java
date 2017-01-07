package AST;
import SemanticAnalysis.ClassNameNotInitializedException;
import SemanticAnalysis.ClassOrFunctionNamesNotInitializedExecption;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class AST_Node 
{
	public int SerialNumber;
	public String className;
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// TODO: delete the implementation. This method should be implemented in each inherited class.
		return null;
	}
	
	public void assertClassNameInitialized() throws ClassNameNotInitializedException
	{
		if(this.className==null)
		{
			throw new ClassNameNotInitializedException();
		}
	}
	
	public void assertClassAndFunctionNamesInitialized(String functionName) throws ClassOrFunctionNamesNotInitializedExecption
	{
		if(this.className==null)
		{
			throw new ClassOrFunctionNamesNotInitializedExecption();
		}
		if(functionName==null)
		{
			throw new ClassOrFunctionNamesNotInitializedExecption();
		}
	}
	
}