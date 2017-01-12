package IR;

public class IR_EXP_LIST extends IR_EXP
{
	public IR_EXP head;
	public IR_EXP_LIST tail;
	public IR_EXP_LIST(IR_EXP head, IR_EXP_LIST tail)
	{
		this.head=head;
		this.tail=tail;
	}
}
