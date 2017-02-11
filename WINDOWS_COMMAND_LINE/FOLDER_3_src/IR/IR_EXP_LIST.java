package IR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CodeGen.CodeGen_Temp;
import SemanticAnalysis.IRExpListGenerateCodeException;
import SemanticAnalysis.SemanticAnalysisException;

public class IR_EXP_LIST extends IR_EXP
{
	public IR_EXP head;
	public IR_EXP_LIST tail;
	public IR_EXP_LIST(IR_EXP head, IR_EXP_LIST tail)
	{
		this.head=head;
		this.tail=tail;
	}
	
	/*
	 * We shouldn't call this function. it is here only because it's an abstract function.
	 */
	public CodeGen_Temp generateCode() throws IRExpListGenerateCodeException
	{
		throw new IRExpListGenerateCodeException();
	}
	
	public List<CodeGen_Temp> generateCodeList() throws IOException, IRExpListGenerateCodeException, SemanticAnalysisException
	{
		List<CodeGen_Temp> argsList = new ArrayList<CodeGen_Temp>();
		argsList.add(head.generateCode());
		IR_EXP_LIST iterator = tail;
		/*
		 * iterates over the exp list and generate code for each exp.
		 * returns a list of temp we received by code generation.
		 */
		while(iterator!=null)
		{
			if(iterator.head!=null)
			{
				argsList.add(iterator.head.generateCode());
			}
			iterator = iterator.tail;
		}
		return argsList;
	}
}
