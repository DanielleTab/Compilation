package AST;

public class AST_STMT_LOCATION_DECL extends AST_STMT 
{
	public AST_TYPE locationType;
	public String locationName;
	public AST_EXP exp;
	
	public AST_STMT_LOCATION_DECL(AST_TYPE locationType, String locationName, AST_EXP exp)
	{
		this.locationType = locationType;
		this.locationName = locationName;
		this.exp = exp;
	}
}
