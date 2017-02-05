package CodeGen;

public class StringNLBuilder 
{
	public StringBuilder builder = new StringBuilder();
	
	public void appendNL(String line)
	{
		builder.append(String.format("%s%s", line, System.lineSeparator()));
	}
	
	
}
