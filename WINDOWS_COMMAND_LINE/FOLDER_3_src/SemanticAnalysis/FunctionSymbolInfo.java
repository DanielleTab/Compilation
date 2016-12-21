package SemanticAnalysis;

import java.util.List;

public class FunctionSymbolInfo extends SymbolInfo{

	public ICTypeInfo returnType;
	public List<ICTypeInfo> argumentsTypes;
	public FunctionSymbolInfo(String symbolName, ICTypeInfo returnType,List<ICTypeInfo> argumentsTypes)
	{
		super(symbolName);
		this.argumentsTypes=argumentsTypes;
		this.returnType=returnType;
	}
	public SymbolType getSymbolType()
	{
		return SymbolType.SYMBOL_TYPE_FUNCTION;
	}
	
	public void addFormal(ICTypeInfo formal)
	{
		this.argumentsTypes.add(formal);
	}
}
