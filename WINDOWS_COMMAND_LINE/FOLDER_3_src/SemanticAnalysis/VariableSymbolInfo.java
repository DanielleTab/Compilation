package SemanticAnalysis;

public class VariableSymbolInfo extends SymbolInfo{

	
	ICTypeInfo variableType;
	
	public VariableSymbolInfo(String symbolName,ICTypeInfo variableType)
	{
		super(symbolName);
		this.variableType=variableType;
	}
	
	public SymbolType getSymbolType()
	{
		return SymbolType.SYMBOL_TYPE_VARIABLE;
	}

	

	
}
