package SemanticAnalysis;


public class ICTypeInfo {
	public String ICType; //"int" for int and "string" for string
	public int pointerDepth; // 0 for regular one.
	
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
}
