package SemanticAnalysis;

import java.util.ArrayList;
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
		if(this.argumentsTypes==null)
		{
			this.argumentsTypes=new ArrayList<ICTypeInfo>();
		}
		this.argumentsTypes.add(formal);
	}
	public boolean equals(Object obj)
	{
		FunctionSymbolInfo comparedSymbol=(FunctionSymbolInfo)obj;
		if((returnType!=null)&&(comparedSymbol.returnType!=null)&&(!returnType.equals(comparedSymbol.returnType)))
		{
			return false;
		}
		if((argumentsTypes!=null)&&(comparedSymbol.argumentsTypes!=null)&&(!argumentsTypes.equals(comparedSymbol.argumentsTypes)))
		{
			return false;
		}
		if((symbolName!=null)&&(comparedSymbol.symbolName!=null)&&(!symbolName.equals(comparedSymbol.symbolName)))
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
