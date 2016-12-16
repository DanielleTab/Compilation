
public class SymbolInfoNode {
	
	public SymbolInfo symbolInfo;
	public SymbolInfoNode hiddenSymbol; //the same name symbol
	public SymbolInfoNode nextSymbolInScope;
	
	public SymbolInfoNode(SymbolInfo symbolInfo)
	{
		this.symbolInfo=symbolInfo;
		
	}
	
	public SymbolInfoNode(SymbolInfo symbolInfo,SymbolInfoNode hiddenSymbol,SymbolInfoNode nextSymbolInScope)
	{
		this.symbolInfo=symbolInfo;
		this.hiddenSymbol=hiddenSymbol;
		this.nextSymbolInScope=nextSymbolInScope;
	}
}
