package AST;
public class AST_METHOD extends AST_FIELD_OR_METHOD
{
	public AST_TYPE returnArgumentType;
	public String methodName;
	public AST_FORMALS_LIST formalsList;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_METHOD(AST_TYPE returnArgumentType,String methodName,AST_FORMALS_LIST formalsList,AST_STMT_LIST body)
	{
		this.returnArgumentType=returnArgumentType;
		this.methodName=methodName;
		this.formalsList=formalsList;
		this.body=body;
	}

}