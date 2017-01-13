package SemanticAnalysis;

public class VariableSymbolInfo extends SymbolInfo
{	
	public ICTypeInfo variableType;
	public int offset=Integer.MAX_VALUE;
	public boolean isField=false;
	public VariableSymbolInfo(String symbolName,ICTypeInfo variableType)
	{
		super(symbolName);
		this.variableType=variableType;
	}
	public VariableSymbolInfo(String symbolName,ICTypeInfo variableType, int offset, boolean isField)
	{
		super(symbolName);
		this.variableType=variableType;
		this.offset=offset;
		this.isField=isField;
	}
	public SymbolType getSymbolType()
	{
		return SymbolType.SYMBOL_TYPE_VARIABLE;
	}

	

	
}
