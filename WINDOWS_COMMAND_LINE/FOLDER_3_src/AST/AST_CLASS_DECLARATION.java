package AST;
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
		if(SymbolTable.insertNewSymbol(classSymbolInfo) == false)
		{
			return null;
		}
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
				}
				
				iterator=iterator.tail;
			}
		}
		SymbolTable.closeCurrentScope();
		return new ICTypeInfo();
	}
	
}
