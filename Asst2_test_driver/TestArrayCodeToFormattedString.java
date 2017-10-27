import org.junit.*;

import static org.junit.Assert.*;

public class TestArrayCodeToFormattedString {

	private Inspector inspector;

	@Before
	public void init(){
		inspector = new Inspector();
	}

	@Test
	public void testInvalid(){
		assertEquals("", inspector.arrayCodeToFormattedString(null));
		assertEquals("", inspector.arrayCodeToFormattedString(""));
		assertEquals("", inspector.arrayCodeToFormattedString("Invalid"));
		assertEquals("", inspector.arrayCodeToFormattedString("Object[]"));
	}

	@Test
	public void testPrimitive(){
		assertEquals("byte[]", inspector.arrayCodeToFormattedString("[B"));
		assertEquals("char[]", inspector.arrayCodeToFormattedString("[C"));
		assertEquals("double[]", inspector.arrayCodeToFormattedString("[D"));
		assertEquals("float[]", inspector.arrayCodeToFormattedString("[F"));
		assertEquals("int[]", inspector.arrayCodeToFormattedString("[I"));
		assertEquals("long[]", inspector.arrayCodeToFormattedString("[J"));
		assertEquals("short[]", inspector.arrayCodeToFormattedString("[S"));
		assertEquals("boolean[]", inspector.arrayCodeToFormattedString("[Z"));
	}

	@Test
	public void testObject(){
		String name = "awefhsdjasldjfh"; //Guaranteed random by fair dice roll
		assertEquals(name + "[]", inspector.arrayCodeToFormattedString("[L" + name));
	}

	@Test
	public void testMultiDimensional(){
		assertEquals("int[][]", inspector.arrayCodeToFormattedString("[[I"));
		assertEquals("int[][][]", inspector.arrayCodeToFormattedString("[[[I"));
		assertEquals("int[][][][]", inspector.arrayCodeToFormattedString("[[[[I"));
	}

	@Test
	public void testMalformatted(){
		assertEquals("", inspector.arrayCodeToFormattedString("[O")); //not an array type
		assertEquals("int[]", inspector.arrayCodeToFormattedString("[ISomeGarbage")); //primitives don't have names
	}
}
