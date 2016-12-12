package AST;
public class AST_FIELD extends AST_FIELD_OR_METHOD
{
	public AST_TYPE type;
	public String fieldName;
	public AST_ID_LIST idsList;
	
	public AST_FIELD(AST_TYPE type,String fieldName,AST_ID_LIST idsList)
	{
		this.type = type;
		this.fieldName=fieldName;
		this.idsList=idsList;
	}

}