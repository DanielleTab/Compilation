package AST;

import IR.IR_LABEL;
import IR.IR_METHOD;
import IR.IR_STMT_LIST;
import SemanticAnalysis.ClassNameNotInitializedException;
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
		
		if(SymbolTable.doesSymbolExistInCurrentScope(methodName))
		{
			return null;
		}
		SymbolInfo symbolWithTheSameName=SymbolTable.searchSymbolInfoInClassAndUp(className, methodName);
		if((symbolWithTheSameName!=null)&&(!(symbolWithTheSameName instanceof FunctionSymbolInfo)))
		{
			// we allow only method override.
			return null;
		}
		SymbolTable.addMethodToClass(className, new FunctionSymbolInfo(methodName,this.body.expectedReturnType,null));
		
		// note: the signature validation is executed in the end of the function, 
		// because we have to compare two functionSymbolInfo - and we build the current one in that function.
		FunctionSymbolInfo methodSymbolInfo = new FunctionSymbolInfo(methodName,this.body.expectedReturnType,null);
		SymbolTable.insertNewSymbol(methodSymbolInfo);
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
		if ((!this.body.expectedReturnType.ICType.equals(ICTypeInfo.IC_TYPE_VOID)) &&
			(!this.body.doesAlwaysReturnValue))
		{
			// The body doesn't always return a value though it should
			DebugPrint.print("AST_METHOD.validate: The body doesn't always return a value though it should");
			return null;
		}
		
		
		SymbolTable.closeCurrentScope();
		if (methodName.equals(SymbolTable.MAIN_FUNC_SYMBOL_NAME))
		{
			if (!methodSymbolInfo.validateMainIsValid())
			{
				DebugPrint.print("AST_METHOD.validate: Invalid main.");
				return null;
			}
		}
		
		return new ICTypeInfo();
	}
	
	public IR_METHOD createIR() throws SemanticAnalysisException
	{
		SymbolTable.addMethodToClass(className, new FunctionSymbolInfo(methodName,this.body.expectedReturnType,null));
		FunctionSymbolInfo methodSymbolInfo = new FunctionSymbolInfo(methodName,this.body.expectedReturnType,null);
		SymbolTable.insertNewSymbol(methodSymbolInfo);
		
		assertClassNameInitialized();
		SymbolTable.createNewScope(); // !!the formals list are like local variables of the method.
		
		if(this.formalsList!=null)
		{
			this.formalsList.className=this.className;
			this.formalsList.functionName=this.methodName;
			this.formalsList.createIR();
		}
		IR_STMT_LIST bodyStmtList;
		if(this.body!=null)
		{
			this.body.className=this.className;
			this.body.functionName=this.methodName;
			bodyStmtList=this.body.createIR();
		}
		else
		{
			bodyStmtList=null;
		}
		SymbolTable.closeCurrentScope();
		
		return new IR_METHOD(new IR_LABEL(String.format("%s_%s", this.className,this.methodName)),bodyStmtList);
	}
}