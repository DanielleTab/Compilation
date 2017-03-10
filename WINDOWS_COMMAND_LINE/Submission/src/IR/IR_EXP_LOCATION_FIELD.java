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
public class IR_EXP_LOCATION_FIELD extends IR_EXP 
{
	public IR_EXP obj;
	public int fieldOffset;

	public IR_EXP_LOCATION_FIELD(IR_EXP obj, int fieldOffset)
	{
		this.obj = obj;
		this.fieldOffset = fieldOffset;
	}
	
	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Generates code which calculates the field's address, 
	 * 			after validating that the object's address isn't null.
	 */
	@Override
	public CodeGen_Temp generateCode() throws IOException, SemanticAnalysisException
	{
		CodeGen_Temp resultTemp = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp objTemp = obj.generateCode();
		CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
		
		// Checking that the object isn't null
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("li %s,0", zeroTemp.getName()));
		printed.appendNL(String.format("beq %s,%s,%s", 
									   objTemp.getName(), 
									   zeroTemp.getName(),
									   IR_Node.ERROR_LABEL_NAME));		
		
		// Generating code for calculating the field's address
		printed.appendNL(String.format("addi %s,%s,%d", 
									   resultTemp.getName(), 
									   objTemp.getName(),
									   fieldOffset));
		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		return resultTemp;
	}
}
