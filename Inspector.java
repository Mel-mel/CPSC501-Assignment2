import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Vector;

public class Inspector {
	
	public void inspect(Object obj, boolean recursive)
    {
	Vector objectsToInspect = new Vector();
	Class ObjClass = obj.getClass();
	
	System.out.println("  inside inspector: " + obj + " (recursive = "+recursive+")");

	//check the class name
	inspectClass(obj, ObjClass, objectsToInspect);
	
	//Check methods a class declares including the exceptions thrown, parameter and return types, and modifiers
	inspectMethods(obj, ObjClass, objectsToInspect);
	
	//Check constructors
	inspectConstructor(obj, ObjClass, objectsToInspect);
	
	//Check fields of the current class
	System.out.println("----Inspecting Fields----");
	inspectFields(obj, ObjClass, objectsToInspect);
	
	//Check all fields that are an object
	/*if(recursive == true)
	{
	    inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive);
    }*/
    }
	
	private void inspectFieldClasses(Object obj, Class classObject, Vector objsToInspect, boolean recursive)
	{
		if(objsToInspect.size() > 0)
		{
			Field aField = classObject.getDeclaredFields()[0];//Something wrong here
			aField.setAccessible(true);
			Enumeration num = objsToInspect.elements();
			for (int i = 0; i < objsToInspect.size(); i++)
			{
				System.out.println(i);
			}
			boolean bool = num.hasMoreElements();
			while (bool = true)
			{
				Field field = (Field) num.nextElement();
				field.setAccessible(true);
				System.out.print("\n----Checking contents of " + field.getName() + "----");
				try {
					inspect(field.get(obj), recursive);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	private void inspectClass(Object obj, Class classObject, Vector objsToInspect)
    {
    	//Get name of declaring class
    	String className = obj.getClass().getName();
    	System.out.println("Name of declaring class: " + className);
    	
    	//Get name of super class
    	Class<?> superName = classObject.getSuperclass();
    	System.out.println("Name of super class: " + superName);
    	
    	//Get name of interfaces of class that implements them and print them out
    	Class[] interfaceNames = classObject.getInterfaces();
    	for(Class classes : interfaceNames)
    	{
    		System.out.println("Name of interfaces: " + interfaceNames);
    	}
    }
	
	private void inspectMethods(Object obj, Class classObject, Vector objsToInspect)
    {
		System.out.println("\n----Inspecting Methods----");
    	Method classMethods[] = classObject.getDeclaredMethods();
    	for (int i = 0; i < classMethods.length; i++)
    	{
    		System.out.println("Name of methods: " + classMethods[i].getName());
    		Class<?>[] exceptions = classMethods[i].getExceptionTypes();
    		Class<?>[] parameters = classMethods[i].getParameterTypes();
    		Class returnType = classMethods[i].getReturnType();
    		int mods = classMethods[i].getModifiers();
    		String modifier = Modifier.toString(mods);
    		for (int k = 0; k < exceptions.length; k++)
    		{
    			System.out.println("Exception type for " + classMethods[i].getName() + ": " + exceptions[k]);
    			System.out.println("Parameter type for " + classMethods[i].getName() + ": " + parameters[k]);
    			
    		}
    		
    		System.out.println("Return type for " + classMethods[i].getName() + ": " + returnType);
			System.out.println("Modifiers for " + classMethods[i].getName() + ": " + modifier + "\n");
    	}
    }
	
	private void inspectConstructor(Object obj, Class classObject, Vector objsToInspect)
	{
		System.out.println("\n----Inspecting constructor----");
		Constructor[] constructors = classObject.getDeclaredConstructors();
		for (int i = 0; i < constructors.length; i++)
		{
			int mods = constructors[i].getModifiers();
			String modifier = Modifier.toString(mods);
			System.out.println("Constructor: " + constructors[i].getName());
			Class<?>[] constructParameters = constructors[i].getParameterTypes();
			for (int j = 0; j < constructParameters.length; j++)
			{
				System.out.println("Constructor parameter types: " + constructParameters[j]);
			}
			
			System.out.println("Modifiers for " + classObject.getName() + ": " + modifier + "\n");
			
		}
	}
	
	private void inspectFields(Object obj, Class classObject, Vector objsToInspect)
	{
		//Get field modifiers, type, and name
		
		Field[] fields = classObject.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			fields[i].setAccessible(true);
			
			int mods = fields[i].getModifiers();
			String modifier = Modifier.toString(mods);
			Object object = fields[i].getType();
			System.out.println("Name of modifier: " + modifier);
			System.out.println("Name of type: " + object);
			System.out.println("Name of field: " + fields[i].getName() + "\n");
			
			Class aClass = fields[i].getType();
			if((!fields[i].getType().isPrimitive()) && (aClass.isArray() == true))
			{
				Object arrayObject;
				try {
					arrayObject = fields[i].get(obj);
					int arrLength = Array.getLength(arrayObject);
					System.out.println("Name of array: " + fields[i].getName());
					System.out.println("Component type of array: " + aClass.getComponentType().getName());
					System.out.println("Length of array: " + arrLength);
					for (int j = 0; j < arrLength; j++)
					{
						System.out.println("Contents of array:" + Array.get(arrayObject, j));
					}
					System.out.println(" ");
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		//Getting field values
		for (int i = 0; i < fields.length; i++)
		{
			Field oneField = classObject.getDeclaredFields()[i];
			oneField.setAccessible(true);
			
			try {
				System.out.println("\nField value of " + fields[i].getName() + ": " + oneField.get(obj));
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
				try {
					//Making vector contain elements of objects
					//objsToInspect.addElement(fields[i]);
					
					System.out.println("Field hash code of " + fields[i].getName() + ": " + obj.hashCode());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			//}
			if(classObject.getSuperclass() != null)
			{
				inspectFields(obj, classObject.getSuperclass(), objsToInspect);
			}
		}
	}
	
	
}
