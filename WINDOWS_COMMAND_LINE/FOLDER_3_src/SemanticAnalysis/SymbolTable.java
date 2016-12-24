package SemanticAnalysis;
import java.util.*;

import SemanticAnalysis.SymbolInfo.SymbolType;

public class SymbolTable {

	public static Hashtable<String,SymbolInfoNode> hashTable;
	public static final String SCOPE_SYMBOL_NAME="<<BSCOPE>>";
	

	/*
	 *  The caller responsibility is to check whether the symbol name does exist
	 *  in the predeccessors or locally in the hash table. 
	 */
	public static void insertNewSymbol(SymbolInfo symbolInfo)
	{
		SymbolInfoNode sameNamePointer=hashTable.get(symbolInfo.symbolName);
		SymbolInfoNode iteratorForScope=hashTable.get(SCOPE_SYMBOL_NAME);
		while(iteratorForScope.nextSymbolInScope!=null)
		{
			iteratorForScope=iteratorForScope.nextSymbolInScope;
		}
		SymbolInfoNode insertedSymbolInfo=new SymbolInfoNode(symbolInfo,sameNamePointer,null);
		iteratorForScope.nextSymbolInScope=insertedSymbolInfo;
		hashTable.put(symbolInfo.symbolName,insertedSymbolInfo);
	}
	
	public static boolean doesSymbolExistInCurrentScope(String symbolName)
	{
		SymbolInfoNode iterator=hashTable.get(SCOPE_SYMBOL_NAME);
		while(iterator!=null)
		{
			if(iterator.symbolInfo.symbolName.equals(symbolName))
			{
				return true;
			}
			iterator=iterator.nextSymbolInScope;
		}
		return false;
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
		SymbolInfo temp=searchSymbolInfoLocallyOrInCurrentClassAndUp(className, className);
		if((temp!=null)&&(temp instanceof ClassSymbolInfo))
		{
			return true;
		}
		
		return false;
	}
	
	// search in the given class and its predecessors.
	// does not search in the hash table.
	// Returns null if the symbol doesn't exist.
	public static SymbolInfo searchSymbolInfoInClassAndUp(String className, String symbolName) throws ClassIsNotInSymbolTableException
	{
		SymbolInfoNode classSymbolInfoNode= hashTable.get(className);
		if(classSymbolInfoNode!=null)
		{
			SymbolInfo symbolInfo=classSymbolInfoNode.symbolInfo;
			if(symbolInfo instanceof ClassSymbolInfo)
			{
				// search inside class
				VariableSymbolInfo fieldFound = ((ClassSymbolInfo) symbolInfo).searchField(symbolName);
				if(fieldFound!=null)
				{
					return fieldFound; // found
				}
				else
				{
					FunctionSymbolInfo methodFound = ((ClassSymbolInfo) symbolInfo).searchMethod(symbolName);
					if(methodFound!=null)
					{
						return methodFound; // found
					}
					else
					{
						// symbolName wasn't found in this class
						String extendedClassName  = ((ClassSymbolInfo) symbolInfo).extendedClassName;
						if(extendedClassName!=null)
						{
							return searchSymbolInfoInClassAndUp(extendedClassName,symbolName); // go up in heritage
						}
						else
						{
							return null; // this is the highest class in heritage, and symbolName hasn'd been found
						}
					}
				}
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
	
	// search in the current class and its predecessors.
	// does search in the hash table.
	// Returns null if the symbol doesn't exist.
	public static SymbolInfo searchSymbolInfoLocallyOrInCurrentClassAndUp(String currenClassName,String symbolName) throws ClassIsNotInSymbolTableException
	{
		SymbolInfoNode classSymbolInfoNode = hashTable.get(symbolName);
		if(classSymbolInfoNode!=null)
		{
			// found it in scopes symbols
			return classSymbolInfoNode.symbolInfo;
		}
		else
		{
			// search in classes heritage
			return searchSymbolInfoInClassAndUp(currenClassName, symbolName);
		}
	}
	
	public static boolean addFormalToMethod(String className,String functionName, VariableSymbolInfo formal)
	{
		// returns true if everything is ok
		if(SymbolTable.doesSymbolExistInCurrentScope(formal.symbolName)==true)
		{
			// The symbol name is already exist in the current scope.
			return false;
		}
		else
		{
			SymbolInfo currentSymbolInfo=SymbolTable.searchSymbolInfoInClassAndUp(className, functionName);
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
	 * Validates the given predeccessor is really a predeccessor of the given descendent. 
	 */
	public static boolean validatePredeccessor(ICTypeInfo predeccessor, ICTypeInfo descendent)
	{
		if(descendent.ICType==ICTypeInfo.IC_TYPE_NULL)
		{
			if((predeccessor.ICType==ICTypeInfo.IC_TYPE_INT) ||(predeccessor.ICType==ICTypeInfo.IC_TYPE_STRING))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		if((predeccessor.pointerDepth!=descendent.pointerDepth) || (doesClassExist(predeccessor.ICType)==false) || (doesClassExist(descendent.ICType)==false))
		{
			return false;
		}
		if((predeccessor.pointerDepth==descendent.pointerDepth)&&(descendent.pointerDepth>0))
		{
			// array does not have subtype
			if(predeccessor.ICType!=descendent.ICType)
			{
				return false;
			}
			else
			{
				return true;
			}
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
	
	public static boolean doesOneMainExistInProgram() throws ClassIsNotInSymbolTableException
	{
		int count=0;
		
		SymbolInfoNode node=hashTable.get(SCOPE_SYMBOL_NAME);
		
		node=node.nextSymbolInScope;
		// iterate over the current scope. it should contains only classes!
		while(node!=null)
		{
			if(!(node.nextSymbolInScope.symbolInfo instanceof ClassSymbolInfo))
			{
				throw new ClassIsNotInSymbolTableException();
			}
			
			ClassSymbolInfo temp=(ClassSymbolInfo)node.nextSymbolInScope.symbolInfo;
			int currClassMainCount=temp.getMainFunctionsCount();
			if(currClassMainCount>1)
			{
				return false;
			}
			else
			{
				count++;
			}
			node=node.nextSymbolInScope;
		}
		return (count==1);
	}
}
