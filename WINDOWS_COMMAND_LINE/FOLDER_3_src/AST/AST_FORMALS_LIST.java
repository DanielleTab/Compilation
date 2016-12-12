package AST;

public class AST_FORMALS_LIST extends AST_Node
{
	public AST_TYPE formal_type;
	public String formal_name;
	public AST_FORMALS_LIST tail;
	
	public AST_FORMALS_LIST(AST_TYPE formal_type,String formal_name,AST_FORMALS_LIST tail)
	{
		this.tail=tail;
		this.formal_type=formal_type;
		this.formal_name=formal_name;
	}
}