package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.TempGenerator;

public class IR_EXP_BINOP extends IR_EXP {
	public IR_EXP left;
	public IR_EXP right;
	public BinOperation operation;
	
	public IR_EXP_BINOP(IR_EXP left, IR_EXP right, BinOperation operation)
	{
		this.left=left;
		this.right=right;
		this.operation=operation;
	}
	
	private boolean isMathematicOperation()
	{
		switch(this.operation)
		{
		case TIMES:
			return true;
		case PLUS: 
			return true;
		case MINUS:
			return true;
		case DIVIDE:
			return true;
		default:
			break;
		}
		return false;
	}
	
	private String findSpecificBinop()
	{
		switch(this.operation)
		{
		case LT:
			return "blt";
		case GT:
			return "bgt";
		case LTE:
			return "ble";
		case GTE:
			return "bge";
		case EQUAL:
			return "beq";
		case NEQUAL: 
			return "bne";
		case TIMES:
			return "mul";
		case PLUS:
			return "add";
		case MINUS:
			return "sub";
		case DIVIDE:
			return "div";
		default:
			break;
		}
		return null;
	}
	
	/*
	 * returns the opposite of the operation.
	 * for example: if op = GT (>) it returns ble(<=)
	 */
	private String getOppositeConditionalOperation()
	{
		switch(this.operation)
		{
		case GT:
			return "ble";
		case GTE:
			return "blt";
		case LT:
			return "bge";
		case LTE:
			return "bgt";
		default:
			break;
		}
		return null;
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		CodeGen_Temp result = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp t1 = left.generateCode();
		CodeGen_Temp t2 = right.generateCode();
		
		StringBuilder printed = new StringBuilder();
		
		if(isMathematicOperation())
		{
			printed.append(String.format("%s %s,%s,%s,%s", findSpecificBinop(),result.getName(),t1.getName(), t2.getName(), AssemblyFilePrinter.NEW_LINE_STRING));
		}
		else
		{
			/*
			 * prints: (for op LT)
			 * li result,0
			 * blt left,right,Label_binop_ok_LABELNUMBER
			 * bge left,right,Label_binop_end_LABELNUMBER
			 * Label_binop_ok_LABELNUMBER: addi result,result,1 # result = result+1
			 * Label_binop_end_LABELNUMBER:
			 */
			int conditionLabelNumber = AssemblyFilePrinter.addLabelIndex();
			String branchLabelOK = String.format("Label_binop_ok_%d", conditionLabelNumber);
			String branchLabelEnd =  String.format("Label_binop_end_%d", conditionLabelNumber);
			printed.append(String.format("li %s,0%s",result.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
			printed.append(String.format("%s %s,%s,%s%s", findSpecificBinop(),t1.getName(), t2.getName(), branchLabelOK,AssemblyFilePrinter.NEW_LINE_STRING));
			printed.append(String.format("%s %s,%s,%s%s", getOppositeConditionalOperation(),t1.getName(),t2.getName(),branchLabelEnd,AssemblyFilePrinter.NEW_LINE_STRING));
			printed.append(String.format("%s:", branchLabelOK));
			printed.append(String.format("addi %s,%s,1%s",result.getName(), result.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
			printed.append(String.format("%s:%s", branchLabelEnd, AssemblyFilePrinter.NEW_LINE_STRING));
		}
		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		
		return result;
	}
}
