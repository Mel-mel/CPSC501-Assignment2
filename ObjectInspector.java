/*==========================================================================
File: ObjectInspector.java
Purpose:Demo Object inspector for the Asst2TestDriver

Location: University of Calgary, Alberta, Canada
Created By: Jordan Kidney
Created on:  Oct 23, 2005
Last Updated: Oct 23, 2005

***********************************************************************
If you are going to reproduce this code in any way for your asignment 
rember to include my name at the top of the file toindicate where you
got the original code from
***********************************************************************


========================================================================*/

import java.util.*;
import java.lang.reflect.*;


public class ObjectInspector
{
    public ObjectInspector() { }

    //-----------------------------------------------------------
    public void inspect(Object obj, boolean recursive)
    {
	Vector objectsToInspect = new Vector();
	Class ObjClass = obj.getClass();

	System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
	
	//inspect the current class
	inspectFields(obj, ObjClass,objectsToInspect);
	
	//check the class name
	inspectClass(obj, ObjClass, objectsToInspect);
	
	//Check methods a class declares including the exceptions thrown, parameter and return types, and modifiers
	inspectMethods(obj, ObjClass, objectsToInspect);
	if(recursive)
	    inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive);
	   
    }
    //-----------------------------------------------------------
    private void inspectFieldClasses(Object obj,Class ObjClass,
				     Vector objectsToInspect,boolean recursive)
    {
	
	if(objectsToInspect.size() > 0 )
	    System.out.println("---- Inspecting Field Classes ----");
	
	Enumeration e = objectsToInspect.elements();
	while(e.hasMoreElements())
	    {
		Field f = (Field) e.nextElement();
		System.out.println("Inspecting Field: " + f.getName() );
		
		try
		    {
			System.out.println("******************");
			inspect( f.get(obj) , recursive);
			System.out.println("******************");
		    }
		catch(Exception exp) { exp.printStackTrace(); }
	    }
    }
    //-----------------------------------------------------------
    private void inspectFields(Object obj,Class ObjClass,Vector objectsToInspect)
  
    {
	
	if(ObjClass.getDeclaredFields().length >= 1)
	    {
		Field f = ObjClass.getDeclaredFields()[0];
		
		f.setAccessible(true);
		
		if(! f.getType().isPrimitive() ) 
		    objectsToInspect.addElement( f );
		
		try
		    {
			
			System.out.println("Field: " + f.getName() + " = " + f.get(obj));
		    }
		catch(Exception e) {}    
	    }

	if(ObjClass.getSuperclass() != null)
	    inspectFields(obj, ObjClass.getSuperclass() , objectsToInspect);
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
    	Method classMethods[] = classObject.getDeclaredMethods();
    	for (Method methods : classMethods)
    	{
    		
    		System.out.println("Name of method: " + classMethods);
    	}
    }
}
