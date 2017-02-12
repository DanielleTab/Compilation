package CodeGen;

import java.util.ArrayList;
import java.util.List;

import SemanticAnalysis.TooManyTempsException;

public class TempGenerator 
{ 
	public static List<CodeGen_Temp> temps;
	public static final int MAX_TEMP_SIZE = 1000;
	
	public static void reset()
	{
		temps = new ArrayList<CodeGen_Temp>(); 
	}
	
	public static CodeGen_Temp getAndAddNewTemp() throws TooManyTempsException
	{
		if (temps.size() == MAX_TEMP_SIZE)
		{
			throw new TooManyTempsException();
		}
		CodeGen_Temp newTemp=new CodeGen_Temp(temps.size());
		temps.add(newTemp);
		return newTemp;
	}
}