package AST;

import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolInfo;
import SemanticAnalysis.SymbolTable;
import Utils.DebugPrint;

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
		
		if(SymbolTable.doesSymbolInfoExistInCurrentScope(methodName))
		{
			return null;
		}
		
		SymbolTable.addMethodToClass(className, new FunctionSymbolInfo(methodName,this.body.expectedReturnType,null));
		
		// note: the signature validation is executed in the end of the function, 
		// because we have to compare two functionSymbolInfo - and we build the current one in that function.
		SymbolTable.insertNewSymbol(new FunctionSymbolInfo(methodName,this.body.expectedReturnType,null));
		if(this.formalsList!=null)
		{
			this.formalsList.functionName=methodName;
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
		if ((this.body.expectedReturnType.ICType != ICTypeInfo.IC_TYPE_VOID) &&
			(!this.body.doesAlwaysReturnValue))
		{
			// The body doesn't always return a value though it should
			DebugPrint.print("AST_METHOD.validate: The body doesn't always return a value though it should");
			return null;
		}
		
		
		SymbolTable.closeCurrentScope();
		
		return new ICTypeInfo();
	}

}