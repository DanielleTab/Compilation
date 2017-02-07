package IR;

import CodeGen.AssemblyFilePrinter;

public class IR_Node {

	public static final int VAR_DEFAULT_INIT_VALUE = 0;
	public static final String ERROR_LABEL_NAME = String.format("Label_%d_ERROR",AssemblyFilePrinter.addLabelIndex()); 
	public static final String END_LABEL_NAME = String.format("Label_%d_END",AssemblyFilePrinter.addLabelIndex());
	

}
