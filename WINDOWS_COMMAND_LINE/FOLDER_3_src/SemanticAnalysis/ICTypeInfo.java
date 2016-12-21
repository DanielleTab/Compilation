package SemanticAnalysis;


public class ICTypeInfo {
	public String ICType; //"int" for int and "string" for string
	public int pointerDepth; // 0 for regular one.
	
	public static final String IC_TYPE_INT = "int";
	public static final String IC_TYPE_STRING = "string";
	public static final String IC_TYPE_NULL = "null";
	public static final String IC_TYPE_VOID = "void";
	
	// usage: for validate function, when we want to say "everything is ok" but we don't have certain ICTypeInfo to return.
	public ICTypeInfo()
	{
		this.ICType=null;
		this.pointerDepth=-1;
	}
	
	public ICTypeInfo(String ICType, int pointerDepth)
	{
		this.ICType=ICType;
		this.pointerDepth=pointerDepth;
	}
	
	public boolean equals(Object obj)
	{
		ICTypeInfo compared=(ICTypeInfo)obj;
		if(this.pointerDepth!=compared.pointerDepth)
		{
			return false;
			
		}
		if(!this.ICType.equals(compared.ICType))
		{
			return false;
		}
		return true;
	}
}
