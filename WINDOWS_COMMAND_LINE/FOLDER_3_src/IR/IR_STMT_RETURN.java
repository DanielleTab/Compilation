package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;
import SemanticAnalysis.SemanticAnalysisException;

public class IR_STMT_RETURN extends IR_STMT
{
	public IR_EXP returnedExpression; // might be null
	public String methodEpilogLabelName;
	
	// TODO: If this is unnecessary, delete it.
	// public CodeGen_Temp returnTempRegister; // might be null
	

	public IR_STMT_RETURN(IR_EXP returnedExpression, String methodEpilogLabelName)
	{
		this.returnedExpression = returnedExpression;
		this.methodEpilogLabelName = methodEpilogLabelName;
	}
	
	/**
	 * @throws SemanticAnalysisException 
	 * @brief	Generates code for the return statement.
	 * 			If some expression is being returned, first generates code which
	 * 			calculates that expression and moves the result into v0.
	 * 			In any case, finishes by generating code which jumps to the method epilog.
	 */
	@Override
	public void generateCode() throws IOException, SemanticAnalysisException
	{
		StringNLBuilder builder = new StringNLBuilder();
		if (returnedExpression != null)
		{
			CodeGen_Temp returnValue = returnedExpression.generateCode();
			builder.appendNL(String.format("mov $v0,%s",returnValue.getName()));
		}
		builder.appendNL(String.format("j %s", methodEpilogLabelName));
		AssemblyFilePrinter.getInstance(null).write(builder.toString());
		
		// TODO: If this is unnecessary, delete it.
		// The original code (with returnTempRegister), no check of null
		/*
		CodeGen_Temp returnValue = returnTempRegister.generateCode();
		AssemblyFilePrinter.getInstance(null).write(String.format("mov $v0,%s%s",returnValue.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
		*/
	}
}
