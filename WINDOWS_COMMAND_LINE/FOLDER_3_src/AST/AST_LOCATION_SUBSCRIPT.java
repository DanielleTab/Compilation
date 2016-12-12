package AST;

public class AST_LOCATION_SUBSCRIPT extends AST_LOCATION
{
	public AST_LOCATION var;
	public AST_EXP subscript;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_LOCATION_SUBSCRIPT(AST_LOCATION var,AST_EXP subscript)
	{
		this.var = var;
		this.subscript = subscript;
	}
}