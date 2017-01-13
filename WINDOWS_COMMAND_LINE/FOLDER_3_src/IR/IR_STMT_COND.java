package IR;

public class IR_STMT_COND extends IR_STMT{
	public IR_EXP cond;
	public IR_STMT body;
	/* 
	 * we should use the label like this:
	 * at the beginning of the body we write: "start: "+label.
	 * one line after the end of the body we write: "end: "+label. 
	 */
	public IR_LABEL label;
	public IR_STMT_COND(IR_EXP cond, IR_STMT body, IR_LABEL label)
	{
		this.cond=cond;
		this.body=body;
		this.label=label;
	}

}
