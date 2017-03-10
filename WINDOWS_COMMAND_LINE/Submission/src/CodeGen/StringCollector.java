package CodeGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import SemanticAnalysis.Pair;

public class StringCollector 
{
	public static List<Pair<String,String>> labelAndStringMappings;
	
	/**
	 * Resets static members.
	 */
	public static void reset()
	{
		labelAndStringMappings = new ArrayList<Pair<String,String>>();
	}
	
	public static String addStringAndLabelMapping(String str)
	{
		int stringNumber = labelAndStringMappings.size();
		String stringLabel = String.format("String_%d",AssemblyFilePrinter.addLabelIndex());
		labelAndStringMappings.add(new Pair<String,String>(stringLabel,str));
		return stringLabel;
	}
	
	public static void printStringsToAssembly() throws IOException
	{
		if(labelAndStringMappings.size() == 0)
		{
			return;
		}
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(".data");
		for(int i=0;i<labelAndStringMappings.size();i++)
		{
			Pair<String,String> currPair = labelAndStringMappings.get(i);
			printed.appendNL(String.format("%s: .asciiz \"%s\"", currPair.left,currPair.right));
		}
		AssemblyFilePrinter.getInstance(null).write(printed.toString());;
	}
}
