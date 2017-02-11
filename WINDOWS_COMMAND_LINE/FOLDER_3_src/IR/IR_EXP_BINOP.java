package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.UnsupportedBinOpException;

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
	private String getOppositeConditionalOperation() throws UnsupportedBinOpException
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
		case EQUAL:
			return "bne";
		case NEQUAL:
			return "beq";
		default:
			break;
		}
		throw new UnsupportedBinOpException();
	}
	
	@Override
	public CodeGen_Temp generateCode() throws IOException, SemanticAnalysisException
	{
		CodeGen_Temp result = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp t1 = left.generateCode();
		CodeGen_Temp t2 = right.generateCode();
		
		StringNLBuilder printed = new StringNLBuilder();
		
		if(isMathematicOperation())
		{
			// TODO: division by zero!
			printed.appendNL(String.format("%s %s,%s,%s", findSpecificBinop(),result.getName(),t1.getName(), t2.getName()));
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
			
			String branchLabelOK = String.format("Label_%d_binop_ok", AssemblyFilePrinter.addLabelIndex());
			String branchLabelEnd =  String.format("Label_%d_binop_end", AssemblyFilePrinter.addLabelIndex());
			printed.appendNL(String.format("li %s,0",result.getName()));
			printed.appendNL(String.format("%s %s,%s,%s", findSpecificBinop(),t1.getName(), t2.getName(), branchLabelOK));
			printed.appendNL(String.format("%s %s,%s,%s", getOppositeConditionalOperation(),t1.getName(),t2.getName(),branchLabelEnd));
			printed.appendNL(String.format("%s:", branchLabelOK));
			printed.appendNL(String.format("addi %s,%s,1",result.getName(), result.getName()));
			printed.appendNL(String.format("%s:", branchLabelEnd));
		}
		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		
		return result;
	}
}
