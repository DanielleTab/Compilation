package AST;

import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.FunctionSymbolInfo;
import SemanticAnalysis.ICTypeInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;

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
		ICTypeInfo returnedType;
		// validate returned type.
		if(returnArgumentType==null)
		{
			returnedType=null;
			// TODO: check return stmts do return nothing.
		}
		else
		{
			returnedType=returnArgumentType.validate(null);
			if(returnedType==null)
			{
				return null;
			}
			
			if((returnedType.ICType!="int")&&(returnedType.ICType!="string")&&
					(SymbolTable.doesClassExist(returnedType.ICType) == false))
			{
				return null;
			}
		}
		
		SymbolTable.addMethodToClass(className, new FunctionSymbolInfo(methodName,returnedType,null));
		this.formalsList.functionName=methodName;
		SymbolTable.createNewScope(); // !!the formals list are like local variables of the method.
		if((this.formalsList!=null)&&(this.formalsList.validate(className)==null))
		{
			return null;
		}
		
		// validates body
		
		// TODO: check the return value in each path!
		// TODO: implement.
		return new ICTypeInfo();
	}

}