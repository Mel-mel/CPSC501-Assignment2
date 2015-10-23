//Melissa Ta, CPSC 501, Assignment 2, UCID#10110850
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ObjectInspectorTest {

	private static ClassA classA;
	private static ClassD classD;
	private static Inspector inspectDriver;
	
	private static Class ObjClassA, ObjClassD;
	private static Vector obj2Inspect;
	
	//Setup objects with predefined values
	@Before
	public void setup()
	{
		classA = new ClassA(40);
		classD = new ClassD(2000);
		obj2Inspect = new Vector();
		ObjClassA = classA.getClass();
		ObjClassD = classD.getClass();
	}
	
	//Test class name
	@Test
	public void testClassName()
	{
		String classAResult = classA.getClass().getName();
		assertEquals(classAResult, ObjClassA.getName());
		
		String classDResult = classD.getClass().getName();
		assertEquals(classDResult, ObjClassD.getName());
	}
	
	//Test method name
	@Test
	public void testMethodName()
	{
		Method[] classAname = classA.getClass().getDeclaredMethods();
		assertEquals("getVal", classAname[3].getName());
		
		Method[] classDname = classD.getClass().getDeclaredMethods();
		assertNotEquals("getVal3", classDname[0].getName());
	}
	
	@Test
	public void testFieldName()
	{
		Field[] fieldA= ObjClassA.getDeclaredFields();
		Field[] fieldD = ObjClassD.getDeclaredFields();
		fieldA[0].setAccessible(true);
		fieldD[0].setAccessible(true);
		try {
			assertEquals(40, fieldA[0].get(classA));
			assertNotEquals(2001, fieldD[0].get(classD));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Teardown
	@After
	public void tearDown()
	{
		classA = null;
		classD = null;
	}
}
