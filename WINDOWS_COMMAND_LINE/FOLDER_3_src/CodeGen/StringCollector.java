package CodeGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import SemanticAnalysis.Pair;

public class StringCollector {
	public static List<Pair<String,String>> LabelAndStringMappings = new ArrayList<Pair<String,String>>();
	
	public static String addStringAndLabelMapping(String str)
	{
		int stringNumber = LabelAndStringMappings.size();
		String stringLabel = String.format("Label_string_%d",stringNumber);
		LabelAndStringMappings.add(new Pair<String,String>(stringLabel,str));
		return stringLabel;
	}
	
	public static void printStringsToAssembly() throws IOException
	{
		StringBuilder printed = new StringBuilder();
		for(int i=0;i<LabelAndStringMappings.size();i++)
		{
			Pair<String,String> currPair = LabelAndStringMappings.get(i);
			printed.append(String.format("%s: %s", currPair.left,currPair.right));
		}
		AssemblyFilePrinter.getInstance(null).write(printed.toString());;
	}
}
