package SemanticAnalysis;

import SemanticAnalysis.SymbolInfo.SymbolType;

public abstract class SymbolInfo {
	public String symbolName;
	public enum SymbolType
	{
		SYMBOL_TYPE_CLASS,
		SYMBOL_TYPE_FUNCTION,
		SYMBOL_TYPE_VARIABLE
	}
	public SymbolInfo(String symbolName)
	{
		this.symbolName=symbolName;
	}
	public abstract SymbolType getSymbolType();
}
