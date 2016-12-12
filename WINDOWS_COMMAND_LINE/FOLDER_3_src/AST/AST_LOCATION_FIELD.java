package AST;

public class AST_LOCATION_FIELD extends AST_LOCATION
{
	public AST_LOCATION var;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_LOCATION_FIELD(AST_LOCATION var,String fieldName)
	{
		this.var = var;
		this.fieldName = fieldName;
	}
}