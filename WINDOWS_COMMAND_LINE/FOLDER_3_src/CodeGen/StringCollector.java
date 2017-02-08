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
		String stringLabel = String.format("String_%d",AssemblyFilePrinter.addLabelIndex());
		LabelAndStringMappings.add(new Pair<String,String>(stringLabel,str));
		return stringLabel;
	}
	
	public static void printStringsToAssembly() throws IOException
	{
		if(LabelAndStringMappings.size() == 0)
		{
			return;
		}
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(".data");
		for(int i=0;i<LabelAndStringMappings.size();i++)
		{
			Pair<String,String> currPair = LabelAndStringMappings.get(i);
			printed.appendNL(String.format("%s: .asciiz \"%s\"", currPair.left,currPair.right));
		}
		AssemblyFilePrinter.getInstance(null).write(printed.toString());;
	}
}
