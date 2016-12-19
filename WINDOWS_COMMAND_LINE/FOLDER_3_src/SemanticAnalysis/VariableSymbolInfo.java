package SemanticAnalysis;

public class VariableSymbolInfo extends SymbolInfo
{	
	public ICTypeInfo variableType;
	public boolean isInitialized;
	
	public VariableSymbolInfo(String symbolName,ICTypeInfo variableType)
	{
		super(symbolName);
		this.variableType=variableType;
		this.isInitialized = false;
	}
	
	public SymbolType getSymbolType()
	{
		return SymbolType.SYMBOL_TYPE_VARIABLE;
	}

	

	
}
