package SemanticAnalysis;

public class VariableSymbolInfo extends SymbolInfo
{	
	public ICTypeInfo variableType;
	public int offset=Integer.MAX_VALUE;
	public VariableSymbolInfo(String symbolName,ICTypeInfo variableType)
	{
		super(symbolName);
		this.variableType=variableType;
	}
	public VariableSymbolInfo(String symbolName,ICTypeInfo variableType, int offset)
	{
		super(symbolName);
		this.variableType=variableType;
		this.offset=offset;
	}
	public SymbolType getSymbolType()
	{
		return SymbolType.SYMBOL_TYPE_VARIABLE;
	}

	

	
}
