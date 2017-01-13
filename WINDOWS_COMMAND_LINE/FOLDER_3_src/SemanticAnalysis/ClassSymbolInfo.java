package SemanticAnalysis;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;



public class ClassSymbolInfo extends SymbolInfo{
	public List<String> virtualFunctionsOrder;
	public Hashtable<String,String> virtualFunctionsTable; // <key, value>: <function name,class name>
	//public List<String> functionNamesInVFT; // TODO: This seems unused.
	public String extendedClassName;
	public List<VariableSymbolInfo> fields;
	public List<FunctionSymbolInfo> methods;
	public int size;
	public ClassSymbolInfo(String symbolName, String extendedClassName,List<VariableSymbolInfo> fields, List<FunctionSymbolInfo> methods)
	{
		super(symbolName);
		
		this.extendedClassName=extendedClassName;
		this.fields=fields;
		this.methods=methods;
		
		// add the size of the parents.
		if(this.extendedClassName!=null)
		{
			ClassSymbolInfo father = SymbolTable.getClassSymbolInfo(extendedClassName);
			this.size=father.size; // this size includes the 32 bit of virtual function table.
			this.virtualFunctionsTable=new Hashtable<>(father.virtualFunctionsTable);
			this.virtualFunctionsOrder=new ArrayList<String>(father.virtualFunctionsOrder);
		}
		else
		{
			// we have to initialize the size to 32 bit because of the virtual function table.
			this.size=SymbolTable.ADDRESS_SIZE;
			this.virtualFunctionsTable=new Hashtable<String,String>();
			this.virtualFunctionsOrder=new ArrayList<String>();
		}
	}
	
	public ClassSymbolInfo(String symbolName, String extendedClassName,List<VariableSymbolInfo> fields, List<FunctionSymbolInfo> methods, int size)
	{
		super(symbolName);
		this.extendedClassName=extendedClassName;
		this.fields=fields;
		this.methods=methods;
		this.size=size;
	}
	
	
	public void addMethod(FunctionSymbolInfo method)
	{
		if(this.methods==null)
		{
			this.methods=new ArrayList<FunctionSymbolInfo>();
		}
		this.methods.add(method);
		if(!this.virtualFunctionsOrder.contains(method.symbolName))
		{
			// We have to find the offset of the new inserted function before add it to the list.
			// each function in the list is 32 bit (of address).
			method.offset=this.virtualFunctionsOrder.size()*SymbolTable.ADDRESS_SIZE;
			this.virtualFunctionsOrder.add(method.symbolName);
		}
		
		this.virtualFunctionsTable.put(method.symbolName, this.symbolName);
	}
	
	public void addField(VariableSymbolInfo field)
	{
		if(this.fields==null)
		{
			this.fields=new ArrayList<VariableSymbolInfo>();
		}
		this.fields.add(field);
		this.size+=field.variableType.getTypeSize();
	}

	public int getMainFunctionsCount()
	{
		int count=0;
		
		if (this.methods == null)
		{
			// No methods at all, in particular no main
			return 0;
		}
		
		for(int i=0;i<this.methods.size();i++)
		{
			FunctionSymbolInfo methodSymbolInfo = this.methods.get(i);
			if(methodSymbolInfo.symbolName.equals(SymbolTable.MAIN_FUNC_SYMBOL_NAME))
			{
				count++;
			}
		}
		return count;
	}
	
	@Override
	public SymbolType getSymbolType() {
		return SymbolType.SYMBOL_TYPE_CLASS;
	}
	
	public VariableSymbolInfo searchField(String symbolName){
		if(this.fields!=null)
		{
		//VariableSymbolInfo searchThis = new VariableSymbolInfo(symbolName,ICTypeInfo variableType)
		for(int i=0;i<this.fields.size();i++)
		{
			if(this.fields.get(i).symbolName.equals( symbolName))
				return this.fields.get(i);
		}
		}
		return null;
	}
	
	public FunctionSymbolInfo searchMethod(String symbolName){
		if(this.methods!=null)
		{
		for(int i=0;i<this.methods.size();i++)
		{
			if(this.methods.get(i).symbolName.equals(symbolName))
				return this.methods.get(i);
		}
		}
		return null;
	}
	
}
