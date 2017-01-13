package SemanticAnalysis;

public class FunctionNotInSymbolTableException extends SemanticAnalysisException 
{
	public FunctionNotInSymbolTableException(String className, String functionName)
	{
		super(String.format("%s.%s"));
	}
}
