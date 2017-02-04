package SemanticAnalysis;

import java.util.ArrayList;
import java.util.List;

import Utils.DebugPrint;

public class FunctionSymbolInfo extends SymbolInfo{

	public ICTypeInfo returnType;
	public List<ICTypeInfo> argumentsTypes;
	public int frameSize = 0; // the size of all local variables
	public int offset=0;
	public int currentIfIndex = 0;
	public int currentWhileIndex = 0;
	
	public FunctionSymbolInfo(String symbolName, ICTypeInfo returnType,List<ICTypeInfo> argumentsTypes)
	{
		super(symbolName);
		this.argumentsTypes=argumentsTypes;
		this.returnType=returnType;
	}
	public FunctionSymbolInfo(String symbolName, ICTypeInfo returnType,List<ICTypeInfo> argumentsTypes, int frameSize)
	{
		super(symbolName);
		this.argumentsTypes=argumentsTypes;
		this.returnType=returnType;
		this.frameSize=frameSize;
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
		if((this.argumentsTypes!=null) && (comparedSymbol.argumentsTypes==null))
		{
			return false;
		}
		if((comparedSymbol.argumentsTypes!=null) && (this.argumentsTypes==null))
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
	
	// Checks if the current function is a valid main function,
	// assuming its name is 'main'.
	public boolean validateMainIsValid() throws FunctionIsNotMainException
	{
		if(!this.symbolName.equals(SymbolTable.MAIN_FUNC_SYMBOL_NAME))
		{
			throw new FunctionIsNotMainException();
		}
		
		if((this.argumentsTypes == null) || (this.argumentsTypes.size()!=1))
		{
			DebugPrint.print("FunctionSymbolInfo.validateMainIsValid: The main method has a wrong number of arguments.");
			return false;
		}
		if(!(this.argumentsTypes.get(0).ICType.equals(ICTypeInfo.IC_TYPE_STRING)))
		{
			DebugPrint.print("FunctionSymbolInfo.validateMainIsValid: The main method's argument has an invalid argument");
			return false;
		}
		if(this.argumentsTypes.get(0).pointerDepth!=1)
		{
			DebugPrint.print("FunctionSymbolInfo.validateMainIsValid: The main method's argument has an invalid argument");
			return false;
		}
		if(!this.returnType.ICType.equals(ICTypeInfo.IC_TYPE_VOID))
		{
			String debugMessage = String.format("FunctionSymbolInfo.validateMainIsValid: The main method's argument has an invalid return type: %s instead of void.", 
					returnType);
			DebugPrint.print(debugMessage);
			return false;
		}
		return true;
	}
	
	public int getNewIfIndex()
	{
		int newIfIndex = currentIfIndex;
		currentIfIndex++;
		return newIfIndex;
	}
	
	public int getNewWhileIndex()
	{
		int newWhileIndex = currentWhileIndex;
		currentWhileIndex++;
		return newWhileIndex;
	}
}
