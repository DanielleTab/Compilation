package AST;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Utils;
import IR.IR_LABEL;
import IR.IR_METHOD;
import IR.IR_STMT_LIST;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

public class AST_METHOD extends AST_FIELD_OR_METHOD
{
	// There's no special field for the method name,
	// the generic currentFunctionName is used instead.
	
	public AST_TYPE returnArgumentType;
	public AST_FORMALS_LIST formalsList;
	public AST_STMT_LIST body;
	public boolean isMainFunc = false;
	public boolean isPrintFunc = false;
	
	public static final String METHOD_LABEL_PREFIX = "Label_0_";
	
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_METHOD(AST_TYPE returnArgumentType,String methodName,AST_FORMALS_LIST formalsList,AST_STMT_LIST body)
	{
		this.returnArgumentType=returnArgumentType;
		this.currentFunctionName = methodName;
		this.formalsList=formalsList;
		this.body=body;
	}
	
	public ICTypeInfo validate(String className) throws SemanticAnalysisException
	{		
		// the void case - and body validation
		if(returnArgumentType==null)
		{
			this.body.expectedReturnType=new ICTypeInfo(ICTypeInfo.IC_TYPE_VOID,0);
		}
		// the other cases
		else
		{
			this.body.expectedReturnType=this.returnArgumentType.validate(className);
			if(this.body.expectedReturnType==null)
			{
				// it means the return type is not validated
				return null;
			}
			
		}
		
		if(SymbolTable.doesSymbolExistInCurrentScope(currentFunctionName))
		{
			return null;
		}
		SymbolInfo symbolWithTheSameName=SymbolTable.searchSymbolInfoInClassAndUp(className, currentFunctionName);
		if((symbolWithTheSameName!=null)&&(!(symbolWithTheSameName instanceof FunctionSymbolInfo)))
		{
			// we allow only method override.
			return null;
		}
		SymbolTable.addMethodToClass(className, new FunctionSymbolInfo(currentFunctionName,this.body.expectedReturnType,null));
		
		// note: the signature validation is executed in the end of the function, 
		// because we have to compare two functionSymbolInfo - and we build the current one in that function.
		FunctionSymbolInfo methodSymbolInfo = new FunctionSymbolInfo(currentFunctionName,this.body.expectedReturnType,null);
		SymbolTable.insertNewSymbol(methodSymbolInfo);
		if(this.formalsList!=null)
		{
			this.formalsList.currentFunctionName=currentFunctionName;
		}
		SymbolTable.createNewScope(); // !!the formals list are like local variables of the method.
		if((this.formalsList!=null)&&(this.formalsList.validate(className)==null))
		{
			return null;
		}

		// body validation
		if(this.body.validate(className)==null)
		{
			// The body isn't valid (returns an incompatible type, or invalid in itself)
			DebugPrint.print("AST_METHOD.validate: The body isn't valid (returns an incompatible type, or invalid in itself)");
			return null;
		}	
		if ((!this.body.expectedReturnType.ICType.equals(ICTypeInfo.IC_TYPE_VOID)) &&
			(!this.body.doesAlwaysReturnValue))
		{
			// The body doesn't always return a value though it should
			DebugPrint.print("AST_METHOD.validate: The body doesn't always return a value though it should");
			return null;
		}
		
		
		SymbolTable.closeCurrentScope();
		if (currentFunctionName.equals(SymbolTable.MAIN_FUNC_SYMBOL_NAME))
		{
			if (!methodSymbolInfo.validateMainIsValid())
			{
				DebugPrint.print("AST_METHOD.validate: Invalid main.");
				return null;
			}
			this.isMainFunc = true;
		}
		if(currentFunctionName.equals(SymbolTable.PRINTINT_FUNC_SYMBOL_NAME) && (className.equals(SymbolTable.PRINT_CLASS_SYMBOL_NAME)))
		{
			this.isPrintFunc = true;
		}
		return new ICTypeInfo();
	}
	
	public IR_METHOD createIR() throws SemanticAnalysisException
	{
		assertClassAndFunctionNamesInitialized();
		FunctionSymbolInfo methodSymbolInfo = new FunctionSymbolInfo(currentFunctionName,this.body.expectedReturnType,null);
		methodSymbolInfo.isMainFunc = this.isMainFunc;
		methodSymbolInfo.isPrintFunc = this.isPrintFunc;
		methodSymbolInfo.functionLabel = String.format("%s%s_%s", METHOD_LABEL_PREFIX,currentClassName,currentFunctionName);
		SymbolTable.addMethodToClass(currentClassName, methodSymbolInfo);
		SymbolTable.insertNewSymbol(methodSymbolInfo);
		
		SymbolTable.createNewScope(); // !!the formals are like local variables of the method.
		
		if(this.formalsList!=null)
		{
			this.formalsList.currentClassName=this.currentClassName;
			this.formalsList.currentFunctionName=this.currentFunctionName;
			this.formalsList.formalFrameOffset = AST_Node.FRAME_OFFSET_OF_FIRST_FORMAL; 
			this.formalsList.createIR();
		}
		
		IR_STMT_LIST bodyStmtList;
		if(this.body!=null)
		{
			this.body.currentClassName=this.currentClassName;
			this.body.currentFunctionName=this.currentFunctionName;
			bodyStmtList=this.body.createIR(); // might return null
		}
		else
		{
			bodyStmtList=null;
		}
		SymbolTable.closeCurrentScope();
		
		
		// save the main label for the code generation process.
		if(isMainFunc)
		{
			SymbolTable.mainFunctionLabel = methodSymbolInfo.functionLabel;
		}
		
		return new IR_METHOD(new IR_LABEL(methodSymbolInfo.functionLabel),
							 bodyStmtList, 
							 methodSymbolInfo.frameSize,
							 isMainFunc,
							 isPrintFunc);
	}
}