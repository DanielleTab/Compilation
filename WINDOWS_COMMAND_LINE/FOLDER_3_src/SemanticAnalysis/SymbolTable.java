package SemanticAnalysis;
import java.util.*;

import SemanticAnalysis.SymbolInfo.SymbolType;

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
	
	// check if class with the received className does exist in the table.
	public static boolean doesClassExist(String className)
	{
		SymbolInfo s = getSymbolInfo(className);
		
		if((s!=null)&&(s.getSymbolType()==SymbolType.SYMBOL_TYPE_CLASS))
		{
			return true;
		}
		
		return false;
	}
	
	// search in the current class and its' predeccessors.
	public static SymbolInfo searchSymbolInfoInClass(String className, String symbolName)
	{
		// TODO: implement
		return null;
	}
	
	
	public static SymbolInfo searchSymbolInfoLocallyOrInCurrentClass(String currenClassName,String symbolName)
	{
		// TODO: implement
		return null;
	}
	
	// should return null if the symbol does not exist
	private static SymbolInfo getSymbolInfo(String symbolName)
	{
		// TODO: implement
		return null;
	}
	
	public static boolean addFormalToMethod(String functionName, VariableSymbolInfo formal)
	{
		// TODO: implement
		// returns true if everything is ok
		
		return false;
		
	}
	
	public static void addMethodToClass(String className, FunctionSymbolInfo methodInfo)
	{
		SymbolInfoNode classSymbolInfoNode= hashTable.get(className);
		if(classSymbolInfoNode!=null)
		{
			SymbolInfo symbolInfo=classSymbolInfoNode.symbolInfo;
			if(symbolInfo instanceof ClassSymbolInfo)
			{
				((ClassSymbolInfo) symbolInfo).addMethod(methodInfo);
			}
			else
			{
				// TODO: fill it
			}
		}
		//TODO: what if className does not exist in the table?
	}
	
	/*
	 * Adds the field info to the class info of the given className.
	 */
	public static void addFieldToClass(String className, VariableSymbolInfo fieldInfo)
	{
		SymbolInfoNode classSymbolInfoNode= hashTable.get(className);
		if(classSymbolInfoNode!=null)
		{
			SymbolInfo symbolInfo=classSymbolInfoNode.symbolInfo;
			if(symbolInfo instanceof ClassSymbolInfo)
			{
				((ClassSymbolInfo) symbolInfo).addField(fieldInfo);
			}
		}
	}
	
	/*
	 * Validates the given predeccessor is really a predeccessor of the given descendent. 
	 */
	public static boolean validatePredeccessor(ICTypeInfo predeccessor, ICTypeInfo descendent)
	{
		if((predeccessor.pointerDepth!=descendent.pointerDepth) || (doesClassExist(predeccessor.ICType)==false) || (doesClassExist(descendent.ICType)==false))
		{
			return false;
		}
		if(predeccessor.ICType==descendent.ICType)
		{
			return true;
		}
		SymbolInfoNode descendentClassNode= hashTable.get(predeccessor.ICType);
		ClassSymbolInfo descendentClass=(ClassSymbolInfo) descendentClassNode.symbolInfo;
		
		if(validatePredeccessor(predeccessor, new ICTypeInfo(descendentClass.extendedClassName,0)))
		{
			return true;
			
			
		}
		return false;
	}
}
