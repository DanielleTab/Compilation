package AST;
import IR.IR_CLASS_DECL;
import IR.IR_METHOD;
import IR.IR_METHOD_LIST;
import SemanticAnalysis.*;
public class AST_CLASS_DECLARATION extends AST_Node 
{
	public String className;
	public String extendsClassName;
	public AST_FIELD_OR_METHOD_LIST fieldsOrMethods;
	
	public AST_CLASS_DECLARATION(String className,String extendsClassName,AST_FIELD_OR_METHOD_LIST l)
	{
		this.fieldsOrMethods=l;
		this.className=className;
		this.extendsClassName=extendsClassName;
	}
	
	// the receivedClassName should be null here - because we are declaring the class right now.
	public ICTypeInfo validate(String receivedClassName) throws SemanticAnalysisException
	{
		// validates the signature
		if(this.extendsClassName!=null)
		{
			if(SymbolTable.doesClassExist(extendsClassName) == false)
			{
				return null;
			}
		}
		
		// inserts into hash table
		ClassSymbolInfo classSymbolInfo=new ClassSymbolInfo(this.className, this.extendsClassName, null, null);
		if(SymbolTable.doesSymbolExistInCurrentScope(className))
		{
			return null;
		}
		SymbolTable.insertNewSymbol(classSymbolInfo);
		SymbolTable.createNewScope();
		// validates body
		if(this.fieldsOrMethods!=null)
		{
			AST_FIELD_OR_METHOD_LIST iterator=this.fieldsOrMethods;
			while(iterator!=null)
			{
				AST_FIELD_OR_METHOD currObj=iterator.head;
				if(currObj instanceof AST_FIELD)
				{
					ICTypeInfo fieldValidation=((AST_FIELD)currObj).validate(className);
					if(fieldValidation == null)
					{
						// something went wrong in the field validation.
						return null;
					}
				}
				else if(currObj instanceof AST_METHOD)
				{
					ICTypeInfo methodValidation=((AST_METHOD)currObj).validate(className);
					if(methodValidation == null)
					{
						// something went wrong in the method validation.
						return null;
					}
					
					// signature validation
					if(extendsClassName!=null)
					{
					FunctionSymbolInfo newInsertedMethod=(FunctionSymbolInfo)SymbolTable.searchSymbolInfoLocallyOrInCurrentClassAndUp(className,((AST_METHOD) currObj).methodName) ;
					FunctionSymbolInfo methodWithTheSameNameInPredesseccor=(FunctionSymbolInfo)SymbolTable.searchSymbolInfoInClassAndUp(extendsClassName,((AST_METHOD) currObj).methodName) ;
					if(methodWithTheSameNameInPredesseccor!=null)
					{
						if(!newInsertedMethod.equals(methodWithTheSameNameInPredesseccor))
						{
							return null;
						}
					}
					}
				}
				
				iterator=iterator.tail;
			}
		}
		SymbolTable.closeCurrentScope();
		return new ICTypeInfo();
	}
	
	// TODO: Implement this using fieldsOrMethods.createIR().
	// This should be completed after the next recitation (11.1.17)
	public IR_CLASS_DECL createIR() throws SemanticAnalysisException
	{
		IR_METHOD_LIST classMethods=null;
		ClassSymbolInfo classSymbolInfo=new ClassSymbolInfo(this.className, this.extendsClassName, null, null);
		SymbolTable.insertNewSymbol(classSymbolInfo);
		SymbolTable.createNewScope();
		if(this.fieldsOrMethods!=null)
		{
			AST_FIELD_OR_METHOD_LIST iterator=this.fieldsOrMethods;
			while(iterator!=null)
			{
				AST_FIELD_OR_METHOD currObj=iterator.head;
				if(currObj instanceof AST_FIELD)
				{
					((AST_FIELD)currObj).className=this.className;
					((AST_FIELD)currObj).createIR();
				}
				else if(currObj instanceof AST_METHOD)
				{
					((AST_METHOD)currObj).className=this.className;
					IR_METHOD methodValidation=((AST_METHOD)currObj).createIR();
					// add the method to the methods list.
					// note: it's ok also for the first element because we put methodValidation as head and null as tail.
					classMethods = new IR_METHOD_LIST(methodValidation, classMethods);
				}
				
				iterator=iterator.tail;
			}
		}
		SymbolTable.closeCurrentScope();
		
		return new IR_CLASS_DECL(classMethods);
	}
	
}
