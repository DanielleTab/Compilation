import java.util.*;
public class SymbolTable {

	public static Hashtable<String,SymbolInfoNode> hashTable;
	public static final String SCOPE_SYMBOL_NAME="<<BSCOPE>>";
	
	public static boolean insertNewSymbol(SymbolInfo symbolInfo)
	{
		//TODO: implement
		return true;
	}
	public static void createNewScope()
	{
		//TODO: implement
	}
	public static void closeCurrentScope()
	{
		// TODO: implement
	}
	public static SymbolInfo getSymbolInfo(String symbolName)
	{
		// TODO: implenent
		return null;
	}
	public static boolean validatePredeccessor(ICTypeInfo predeccessor, ICTypeInfo descendent)
	{
		//TODO: implemtnt
		return true;
	}
}
