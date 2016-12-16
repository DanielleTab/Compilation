
public class ClassSymbolInfo extends SymbolInfo{
	
	public String extendedClassName;
	public VariableSymbolInfo[] fields;
	public FunctionalInterface[] methods;
	
	public ClassSymbolInfo(String symbolName, String extendedClassName,VariableSymbolInfo[] fields, FunctionalInterface[] methods)
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
}
