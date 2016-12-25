package SemanticAnalysis;

import java.util.ArrayList;
import java.util.List;

public class ClassSymbolInfo extends SymbolInfo{
	
	public String extendedClassName;
	public List<VariableSymbolInfo> fields;
	public List<FunctionSymbolInfo> methods;
	
	public ClassSymbolInfo(String symbolName, String extendedClassName,List<VariableSymbolInfo> fields, List<FunctionSymbolInfo> methods)
	{
		super(symbolName);
		this.extendedClassName=extendedClassName;
		this.fields=fields;
		this.methods=methods;
	}
	
	
	public void addMethod(FunctionSymbolInfo method)
	{
		if(this.methods==null)
		{
			this.methods=new ArrayList<FunctionSymbolInfo>();
		}
		this.methods.add(method);
	}
	public void addField(VariableSymbolInfo field)
	{
		if(this.fields==null)
		{
			this.fields=new ArrayList<VariableSymbolInfo>();
		}
		this.fields.add(field);
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
			if(this.methods.get(i).isMain())
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
