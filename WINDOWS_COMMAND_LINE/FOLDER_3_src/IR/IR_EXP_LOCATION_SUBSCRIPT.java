package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SemanticAnalysisException;

/* 
 * This class extends IR_EXP since it calculates a value.
 * Notice that this isn't the AST_EXP.
 * The value is the address itself and not the content.
 * */
public class IR_EXP_LOCATION_SUBSCRIPT extends IR_EXP 
{
	public IR_EXP arrayBase;
	public IR_EXP index;
	
	public IR_EXP_LOCATION_SUBSCRIPT(IR_EXP arrayBase, IR_EXP index)
	{
		this.arrayBase = arrayBase;
		this.index = index;
	}
	
	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Generates code which calculates the address of the array's subscript,
	 * 			after validating it doesn't exceed the array's bounds. 
	 */
	@Override
	public CodeGen_Temp generateCode() throws IOException, SemanticAnalysisException
	{
		CodeGen_Temp resultTemp = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp arrayBaseTemp = arrayBase.generateCode();
		CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
		
		// Checking that the array base isn't null
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("li %s,0", zeroTemp.getName()));
		printed.appendNL(String.format("beq %s,%s,%s", 
									   arrayBaseTemp.getName(), 
									   zeroTemp.getName(),
									   IR_Node.ERROR_LABEL_NAME));		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		// Checking that the index doesn't exceed array's bounds
		CodeGen_Temp offsetTemp = index.generateCode();
		CodeGen_Temp arrayLengthTemp = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp registerForFour = TempGenerator.getAndAddNewTemp();
		
		printed = new StringNLBuilder();
		printed.appendNL(String.format("lw %s,0(%s)", 
									   arrayLengthTemp.getName(), 
									   arrayBaseTemp.getName()));
		printed.appendNL(String.format("bge %s,%s,%s", 
									   offsetTemp.getName(), 
									   arrayLengthTemp.getName(),
									   IR_Node.ERROR_LABEL_NAME));
		
		printed.appendNL(String.format("blt %s,%s,%s", 
				   offsetTemp.getName(), 
				   zeroTemp.getName(),
				   IR_Node.ERROR_LABEL_NAME));

		// Calculating the address of the array's element
		printed.appendNL(String.format("addi %s,%s,1", 
									   offsetTemp.getName(), 
									   offsetTemp.getName()));
		
		// multiply offsetTemp by 4.
		printed.appendNL(String.format("li %s,4", registerForFour.getName()));
		printed.appendNL(String.format("mul %s,%s,%s", 
									   offsetTemp.getName(),
									   offsetTemp.getName(),
									   registerForFour.getName()));
		
		printed.appendNL(String.format("add %s,%s,%s", 
									   resultTemp.getName(),
									   offsetTemp.getName(),
									   arrayBaseTemp.getName()));
		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		return resultTemp;
	}
}
