

public class ICTypeInfo {
	public String ICType; //"int" for int and "string" for string
	public int pointerDepth; // 0 for regular one.
	public ICTypeInfo(String ICType, int pointerDepth)
	{
		this.ICType=ICType;
		this.pointerDepth=pointerDepth;
	}
}
