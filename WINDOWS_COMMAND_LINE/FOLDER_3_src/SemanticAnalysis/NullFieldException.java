package SemanticAnalysis;

public class NullFieldException extends SemanticAnalysisException 
{
	// Should be used when a field is null even though it shouldn't
	// (sometimes a field can be null, so not in these cases)
	public NullFieldException(String fieldName)
	{
		super("The field " + fieldName + " is null");
	}
}
