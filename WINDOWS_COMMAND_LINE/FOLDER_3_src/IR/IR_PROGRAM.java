package IR;

import java.io.IOException;

public class IR_PROGRAM extends IR_Node {

	// fields
	public IR_CLASS_DECL_LIST classDeclList;
	
	// C'tor
	public IR_PROGRAM(IR_CLASS_DECL_LIST classDeclList)
	{
		this.classDeclList = classDeclList;
	}

	public void generateCode() throws IOException 
	{
		// TODO: jmp to main, class decl, main label, main wrap, call to our main, label end/error.
		this.classDeclList.generateCode();
	}
	
}
