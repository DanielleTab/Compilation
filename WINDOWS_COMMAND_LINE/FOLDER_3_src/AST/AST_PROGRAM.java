package AST;

public class AST_PROGRAM extends AST_Node 
{
	public AST_CLASS_DECLARATION_LIST l;
	public AST_PROGRAM(AST_CLASS_DECLARATION_LIST l)
	{
		this.l=l;
	}
}
