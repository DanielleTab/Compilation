package IR;

import java.io.IOException;

public abstract class IR_STMT extends IR_Node 
{
	public abstract void generateCode() throws IOException;
}
