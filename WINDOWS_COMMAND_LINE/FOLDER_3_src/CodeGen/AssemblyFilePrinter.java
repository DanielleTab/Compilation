package CodeGen;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolInfoNode;
import SemanticAnalysis.SymbolTable;

/*
 * Singleton implementation.
 * Use this class as following:
 * AssemblyFilePrinter.getInstance(null).write("everything you would like");
 * 
 */

public class AssemblyFilePrinter 
{
	private FileWriter fileWriter = null;
	public static AssemblyFilePrinter instance;
	public static int labelIndex;
	
	public final static String NEW_LINE_STRING = System.lineSeparator();
	
	/**
	 * Resets static members.
	 */
	public static void reset()
	{
		instance = null;
		labelIndex = 0;
	}
	
	protected AssemblyFilePrinter(String filePath) throws IOException
	{
		fileWriter = new FileWriter(filePath);
	}
	
	/*
	 * The filePath should be null in all of the invokes except the calling from the main function,
	 * where we have the actual file path.
	 * So the singleton creation should be called from the main function.
	 */
	public static FileWriter getInstance(String filePath) throws IOException
	{
		if(instance == null)
		{
			instance = new AssemblyFilePrinter(filePath);
		}
		return instance.fileWriter;
	}
	
	public static int addLabelIndex()
	{
		labelIndex++;
		return labelIndex;
	}
	
	private static void printVirtualFunctionsTableForClass(ClassSymbolInfo classSymbolInfo) throws IOException
	{
		StringBuilder printed = new StringBuilder();
		if(classSymbolInfo.virtualFunctionsOrder.size()==0)
		{
			return;
		}
		printed.append(String.format("%s: .word ",classSymbolInfo.getVFTableLabel()));
		List<String> virtualFunctionsOrder = classSymbolInfo.virtualFunctionsOrder;
		Hashtable<String,String> virtualFunctionsTable = classSymbolInfo.virtualFunctionsTable;
		for(int i=0;i<virtualFunctionsOrder.size();i++)
		{
			printed.append(virtualFunctionsTable.get(virtualFunctionsOrder.get(i)));
			
			// add comma if it's not the last element in the list
			if(i!=virtualFunctionsOrder.size()-1)
			{
				printed.append(",");
			}
		}
		AssemblyFilePrinter.getInstance(null).write(printed.toString()+System.lineSeparator());
	}
	
	public static void printVirtualFunctionsTablesAndUpdateVFTAdresses() throws IOException
	{
		List<SymbolInfoNode> hashTableValues = new ArrayList<SymbolInfoNode>(SymbolTable.hashTable.values());
		for(int i=0;i<hashTableValues.size();i++)
		{
			SymbolInfoNode temp = hashTableValues.get(i);
			if(temp.symbolInfo instanceof ClassSymbolInfo)
			{
				 printVirtualFunctionsTableForClass((ClassSymbolInfo)temp.symbolInfo);
			}
		}
	}
	
	
}
