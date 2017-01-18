package IR;

import CodeGen.StringCollector;

public class IR_LITERAL_STRING extends IR_LITERAL{
	
	public String quote;
	public IR_LABEL label;
	public IR_LITERAL_STRING(String quote) 
	{
		this.quote=quote;
		this.label = new IR_LABEL(StringCollector.addStringAndLabelMapping(this.quote));
	}
}
