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
	
	/**
	 * @brief	Checks if this ICTypeInfo has the same ICType as the given one,
	 * 			and it is flat (meaning, it's not an array).
	 * @param 	otherICType
	 * @return	true if the ICTypes match and this is not an array, false otherwise.
	 */
	public boolean isFlatICType(String otherICType)
	{
		return (ICType.equals(otherICType) &&
				pointerDepth == 0);
	}
	
	public boolean isICClass()
	{
		boolean isSpecialICType = (ICType.equals(IC_TYPE_INT)) 		||
								  (ICType.equals(IC_TYPE_STRING))	||
								  (ICType.equals(IC_TYPE_VOID)) 	||
							      (ICType.equals(IC_TYPE_NULL));
		
		return ((!isSpecialICType) &&
				(pointerDepth == 0));
	}
	
	@Override
	public String toString()
	{
		String rep = ICType;
		
		int i = pointerDepth;
		while (i > 0)
		{
			rep += "[]";
			i--;
		}
		
		return rep;
	}
}
