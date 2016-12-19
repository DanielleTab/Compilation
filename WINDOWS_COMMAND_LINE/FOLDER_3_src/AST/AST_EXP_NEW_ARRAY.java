package AST;

public class AST_EXP_NEW_ARRAY extends AST_EXP{
	public AST_TYPE type;
	public AST_EXP size;
	
	public AST_EXP_NEW_ARRAY(AST_TYPE type, AST_EXP size){
		this.type = type;
		this.size = size;
	}
}
