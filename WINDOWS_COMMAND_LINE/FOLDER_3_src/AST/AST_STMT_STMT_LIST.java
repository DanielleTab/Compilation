package AST;

public class AST_STMT_STMT_LIST extends AST_STMT 
{
	public AST_STMT_LIST stmtList;
	
	public AST_STMT_STMT_LIST(AST_STMT_LIST stmtList)
	{
		this.stmtList = stmtList;
	}
}
