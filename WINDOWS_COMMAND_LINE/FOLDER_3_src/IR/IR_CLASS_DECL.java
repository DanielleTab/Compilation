package IR;

import java.io.IOException;

public class IR_CLASS_DECL extends IR_Node 
{
	// fields
	// (Maybe should also create a label with the class name?) IR_LABEL name;
	public IR_METHOD_LIST methods;
	
	// C'tor
	public IR_CLASS_DECL(IR_METHOD_LIST methods)
	{
		this.methods = methods;
	}
	
	public void generateCode() throws IOException
	{
		methods.generateCode();
	}
}
