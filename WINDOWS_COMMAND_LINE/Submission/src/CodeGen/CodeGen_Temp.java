package CodeGen;

public class CodeGen_Temp {
	public int tempIndex;
	public CodeGen_Temp(int tempIndex)
	{
		this.tempIndex=tempIndex;
	}
	
	public String getName()
	{
		return String.format("Temp_%d", tempIndex);
	}
}
