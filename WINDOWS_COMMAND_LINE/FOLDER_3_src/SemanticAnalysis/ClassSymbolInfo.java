package SemanticAnalysis;

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
	
	public ICTypeInfo getFieldICType(String fieldName)
	{
		//TODO: implenent
		return null;
	}
	public ICTypeInfo getMethodReturnType(String methodName, ICTypeInfo[] arguments)
	{
		//TODO: implement
		return null;
	}
	
	public void addMethod(FunctionSymbolInfo method)
	{
		this.methods.add(method);
	}
	public void addField(VariableSymbolInfo field)
	{
		this.fields.add(field);
	}

	
	@Override
	public SymbolType getSymbolType() {
		return SymbolType.SYMBOL_TYPE_CLASS;
	}
}
