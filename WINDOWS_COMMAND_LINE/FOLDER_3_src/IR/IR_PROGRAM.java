package IR;

public class IR_PROGRAM extends IR_Node {

	// fields
	public IR_CLASS_DECL_LIST classDeclList;
	
	// C'tor
	public IR_PROGRAM(IR_CLASS_DECL_LIST classDeclList)
	{
		this.classDeclList = classDeclList;
	}
	
}
