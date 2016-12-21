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
	public boolean equals(Object obj)
	{
		FunctionSymbolInfo comparedSymbol=(FunctionSymbolInfo)obj;
		if(!returnType.equals(comparedSymbol.returnType))
		{
			return false;
		}
		if(!argumentsTypes.equals(comparedSymbol.argumentsTypes))
		{
			return false;
		}
		if(!symbolName.equals(comparedSymbol.symbolName))
		{
			return false;
		}
		return true;
	}
	
	public boolean isMain()
	{
		if(!this.symbolName.equals("main"))
		{
			return false;
		}
		if(this.argumentsTypes.size()!=1)
		{
			return false;
		}
		if(this.argumentsTypes.get(0).ICType!=ICTypeInfo.IC_TYPE_STRING)
		{
			return false;
		}
		if(this.argumentsTypes.get(0).pointerDepth!=1)
		{
			return false;
		}
		return true;
	}
}
