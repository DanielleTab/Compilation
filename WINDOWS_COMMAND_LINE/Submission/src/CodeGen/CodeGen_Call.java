package CodeGen;

import IR.BinOperation;
import IR.IR_EXP;
import IR.IR_EXP_BINOP;
import IR.IR_EXP_CALL;
import IR.IR_EXP_LIST;
import IR.IR_EXP_MEM;
import IR.IR_EXP_NEW_ARRAY;
import IR.IR_EXP_NEW_CLASS;
import IR.IR_LITERAL;
import IR.IR_LITERAL_CONST;
import IR.IR_LITERAL_STRING;
import IR.IR_METHOD;
import IR.IR_METHOD_LIST;
import IR.IR_STMT_STORE;
import IR.IR_STMT_WHILE;
import IR.IR_TEMP;
import SemanticAnalysis.TooManyTempsException;

public class CodeGen_Call {
	
	public String convertOpToMipsBinop(BinOperation op, CodeGen_Temp left, CodeGen_Temp right, CodeGen_Temp result)
	{
		switch(op)
		{
		case PLUS:
			return String.format("add %s,%s,%s", result.getName(),left.getName(), right.getName());
		case MINUS:
			return String.format("sub %s,%s,%s",result.getName(),left.getName(), right.getName());
		case DIVIDE:
			return String.format("div %s,%s,%s", result.getName(), left.getName(), right.getName());
		case TIMES:
			return String.format("mult %s,%s,%s", result.getName(), left.getName(), right.getName());
		case LT:
			return String.format("slt %s,%s,%s",result.getName(), left.getName(), right.getName());
		case LTE:
			return String.format("",result.getName(),left.getName(), right.getName());
		case GT:
			break;
		case GTE:
			break;
		case EQUAL:
			break;
		case NEQUAL:
			break;
		}
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_EXP_BINOP binopExp) throws TooManyTempsException
	{
		CodeGen_Temp t1 = TempGenerator.getAndAddNewTemp();
		//Temp_temp t2 = codeGen_exp(binopExp.left);
		//Temp_temp t3 = codeGen_exp(binopExp.right);
		//System.out.printf(convertOpToMipsBinop(binopExp.operation, t2,t3, t1));
		return t1;
	}
		
	public CodeGen_Temp codeGen_exp(IR_TEMP temp)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_LITERAL_CONST literalConst)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_LITERAL_STRING literalString)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_EXP_CALL callExp)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_EXP_LIST expList)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_EXP_MEM memExp)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_EXP_NEW_ARRAY newArrayExp)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_exp(IR_EXP_NEW_CLASS newClass)
	{
		return null;
	}
	
	
	public CodeGen_Temp codeGen_move(IR_STMT_STORE moveStmt)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_expCall(IR_EXP_CALL expCall)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_expMem(IR_EXP_MEM expMem)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_LiteralConst(IR_LITERAL_CONST literalConst)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_LiteralString(IR_LITERAL_STRING literalString)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_expNewArray(IR_EXP_NEW_ARRAY expNewArray)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_method(IR_METHOD method)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_method_list(IR_METHOD_LIST methodsList)
	{
		return null;
	}
	
	public CodeGen_Temp codeGen_STMT_WHILE(IR_STMT_WHILE stmtWhile)
	{
		return null;
	}
	
	
}
