package AST;

public class AST_CLASS_DECLARATION extends AST_Node 
{
	public String className;
	public String extendsClassName;
	public AST_FIELD_OR_METHOD_LIST fieldsOrMethods;
	
	public AST_CLASS_DECLARATION(String className,String extendsClassName,AST_FIELD_OR_METHOD_LIST l)
	{
		this.fieldsOrMethods=l;
		this.className=className;
		this.extendsClassName=extendsClassName;
	}
}
