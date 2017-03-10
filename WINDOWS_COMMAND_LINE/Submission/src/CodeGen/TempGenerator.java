package CodeGen;

import java.util.ArrayList;
import java.util.List;

import SemanticAnalysis.TooManyTempsException;

public class TempGenerator 
{ 
	public static List<CodeGen_Temp> temps;
	
	// Uncomment this line for testing.
	//public static final int MAX_TEMP_SIZE = 4000;
	
	public static void reset()
	{
		temps = new ArrayList<CodeGen_Temp>(); 
	}
	
	public static CodeGen_Temp getAndAddNewTemp() throws TooManyTempsException
	{
		// Uncomment the following block when testing.
		/*
		if (temps.size() == MAX_TEMP_SIZE)
		{
			throw new TooManyTempsException();
		}
		*/
		CodeGen_Temp newTemp=new CodeGen_Temp(temps.size());
		temps.add(newTemp);
		return newTemp;
	}
}