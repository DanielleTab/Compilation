package CodeGen;

public class StringNLBuilder 
{
	public StringBuilder builder = new StringBuilder();
	
	public void appendNL(String line)
	{
		this.builder.append(String.format("%s%s", line, System.lineSeparator()));
	}
	
	public String toString()
	{
		return this.builder.toString();
	}
}
