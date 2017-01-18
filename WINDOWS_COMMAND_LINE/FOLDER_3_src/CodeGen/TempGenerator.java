package CodeGen;

import java.util.ArrayList;
import java.util.List;

public class TempGenerator {
	public static List<CodeGen_Temp> temps=new ArrayList<CodeGen_Temp>();
	
	public static CodeGen_Temp getAndAddNewTemp()
	{
		CodeGen_Temp newTemp=new CodeGen_Temp(temps.size());
		temps.add(newTemp);
		return newTemp;
	}
}