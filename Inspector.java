import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;

public class Inspector {
	
	public void inspect(Object obj, boolean recursive)
    {
	Vector objectsToInspect = new Vector();
	Class ObjClass = obj.getClass();
	
	System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
	
	//inspect the current class
	//inspectFields(obj, ObjClass,objectsToInspect);
	
	//check the class name
	inspectClass(obj, ObjClass, objectsToInspect);
	
	//Check methods a class declares including the exceptions thrown, parameter and return types, and modifiers
	inspectMethods(obj, ObjClass, objectsToInspect);
	
	//Check constructors
	inspectConstructor(obj, ObjClass, objectsToInspect);
	//if(recursive)
	   // inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive);
	   
   // }
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
		Constructor[] constructors = classObject.getConstructors();
		for (int i = 0; i < constructors.length; i++)
		{
			int mods = constructors[i].getModifiers();
			String modifier = Modifier.toString(mods);
			System.out.println("Constructor: " + constructors[i]);
			Class<?>[] constructParameters = constructors[i].getParameterTypes();
			for (int j = 0; j < constructParameters.length; j++)
			{
				System.out.println("Constructor parameter types: " + constructParameters[j]);
			}
			
			System.out.println("Modifiers for " + classObject + ": " + modifier + "\n");
			
		}
	}
}
