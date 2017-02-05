package AST;

import IR.IR_EXP;
import IR.IR_LITERAL_CONST;
import IR.IR_LITERAL_STRING;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;

public class AST_EXP_LITERAL extends AST_EXP{
	public AST_LITERAL l;
	
	public AST_EXP_LITERAL(AST_LITERAL l)
	{
		this.l=l;
	}


	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{
		return l.validate(className);
	}
	
	// create IR_LITERAL_CONST or IR_LITERAL_STRING according to the local field.
	public IR_EXP createIR()
	{
		if(this.l instanceof AST_LITERAL_INTEGER){
			return new IR_LITERAL_CONST(((AST_LITERAL_INTEGER) this.l).i);
		}else if(this.l instanceof AST_LITERAL_QUOTE){
			return new IR_LITERAL_STRING(((AST_LITERAL_QUOTE) this.l).str);
		}else if(this.l instanceof AST_LITERAL_NULL){
			return new IR_LITERAL_CONST(0);
		}

		// This will never happen
		return null; 
	}
}
