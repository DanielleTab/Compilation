package AST;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public abstract class AST_Node 
{
	public int SerialNumber;
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		// TODO: delete the implementation. This method should be implemented in each inherited class.
		return null;
	}
	
}