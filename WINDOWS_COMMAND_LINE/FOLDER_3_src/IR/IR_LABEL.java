package IR;

public class IR_LABEL extends IR_Node 
{
	// fields
	public String name;
	
	// C'tor
	public IR_LABEL(String name)
	{
		 // add the "Label_" because of the simulator lexer.
		this.name = String.format("Label_%s", name);
	}
}
