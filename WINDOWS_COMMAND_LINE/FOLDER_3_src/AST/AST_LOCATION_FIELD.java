package AST;

public class AST_LOCATION_FIELD extends AST_LOCATION
{
	public AST_EXP var;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_LOCATION_FIELD(AST_EXP var,String fieldName)
	{
		this.var = var;
		this.fieldName = fieldName;
	}
}