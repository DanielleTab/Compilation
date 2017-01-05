package AST;

import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;
import IR.IR_PROGRAM;

public class AST_PROGRAM extends AST_Node 
{
	public AST_CLASS_DECLARATION_LIST l;
	
	public AST_PROGRAM(AST_CLASS_DECLARATION_LIST l)
	{
		this.l=l;
	}
	
	
	// returns ICTypeInfo = null if the program is not validated
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		SymbolTable.createNewScope();
		AST_CLASS_DECLARATION_LIST iterator=l;
		while((iterator!=null) && (iterator.head!=null))
		{
			if(iterator.head.validate(className)==null)
			{
				return null;
			}
			iterator=iterator.tail;
		}

		if(!SymbolTable.doesOneMainExistInProgram())
		{
			DebugPrint.print("AST_PROGRAM.validate: The program doesn't contain exactly one valid main method");
			return null;
		}
		SymbolTable.closeCurrentScope();
		return new ICTypeInfo();
	}
	
	public IR_PROGRAM createIR()
	{
		return new IR_PROGRAM(l.createIR());
	}
	
}
