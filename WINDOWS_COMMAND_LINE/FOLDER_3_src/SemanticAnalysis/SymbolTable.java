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
		SymbolInfoNode node=SymbolTable.hashTable.get(SCOPE_SYMBOL_NAME);
		SymbolInfoNode newNode=new SymbolInfoNode(null,null,null);
		if(node!=null)
		{
			newNode.hiddenSymbol=node;
		}
		
		SymbolTable.hashTable.put(SCOPE_SYMBOL_NAME,newNode);
	}
	public static void closeCurrentScope()
	{
		SymbolInfoNode node=SymbolTable.hashTable.get(SCOPE_SYMBOL_NAME);
		
		// temp is the first scope element
		SymbolInfoNode temp=node.nextSymbolInScope;
		while(temp!=null)
		{
			SymbolInfoNode entry=SymbolTable.hashTable.get(temp.symbolInfo.symbolName);
			SymbolTable.hashTable.put(temp.symbolInfo.symbolName,entry.hiddenSymbol);
			temp=temp.nextSymbolInScope;
		}
		
		SymbolTable.hashTable.put(SCOPE_SYMBOL_NAME, node.hiddenSymbol);
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
	
	public static SymbolInfo searchSymbolInfoInCurrentScope(String symbolName)
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
	
	public static boolean addFormalToMethod(String className,String functionName, VariableSymbolInfo formal)
	{
		// returns true if everything is ok
		if(SymbolTable.searchSymbolInfoInCurrentScope(formal.symbolName)!=null)
		{
			// The symbol name is already exist in the current scope.
			return false;
		}
		else
		{
			SymbolInfo currentSymbolInfo=SymbolTable.searchSymbolInfoInClass(className, functionName);
			if(currentSymbolInfo instanceof FunctionSymbolInfo)
			{
				FunctionSymbolInfo currentMethod=(FunctionSymbolInfo)currentSymbolInfo;
				currentMethod.addFormal(formal.variableType);
			}
			else
			{
				return false;
			}
		}
		return true;
		
	}
	
	// the assumption: the class is already in the table.
	// this exception is for us, the developers: it means we didn't insert the class to the table before calling that function.
	public static void addMethodToClass(String className, FunctionSymbolInfo methodInfo) throws ClassIsNotInSymbolTableException
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
				throw new ClassIsNotInSymbolTableException();
			}
		}
		else
		{
			throw new ClassIsNotInSymbolTableException();
		}
	}
	
	/*
	 * Adds the field info to the class info of the given className.
	 */
	public static void addFieldToClass(String className, VariableSymbolInfo fieldInfo) throws ClassIsNotInSymbolTableException
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
		else
		{
			throw new ClassIsNotInSymbolTableException();
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
