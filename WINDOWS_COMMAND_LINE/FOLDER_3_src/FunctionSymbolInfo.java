
public class FunctionSymbolInfo extends SymbolInfo{

	public ICTypeInfo returnType;
	public ICTypeInfo[] argumentsTypes;
	public FunctionSymbolInfo(String symbolName, ICTypeInfo returnType,ICTypeInfo[] argumentsTypes)
	{
		super(symbolName);
		this.argumentsTypes=argumentsTypes;
		this.returnType=returnType;
	}
	public SymbolType getSymbolType()
	{
		return SymbolType.SYMBOL_TYPE_FUNCTION;
	}
}
