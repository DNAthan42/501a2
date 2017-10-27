import org.junit.*;
import static org.junit.Assert.*;

public class TestGetArrayType {

	private Inspector inspector;

	@Before
	public void init(){
		inspector = new Inspector();
	}

	@Test
	public void testValid(){
		for (int i = 0; i < Inspector.ARRAY_TYPE_CODES.length; i++){
			assertEquals(i, inspector.getArrayType(Inspector.ARRAY_TYPE_CODES[i]));
		}
		assertEquals(Inspector.ARRAY_TYPE_CODES.length, inspector.getArrayType("L")); //check for reference type aswell

	}

	@Test
	public void testInvalid(){
		assertEquals(-1, inspector.getArrayType("O")); // not an array type
		assertEquals(-1, inspector.getArrayType("BC")); // cant be both
		assertEquals(-1, inspector.getArrayType("1")); //obvious padding
	}

	@Test
	public void testLower(){
		for (int i = 0; i < Inspector.ARRAY_TYPE_CODES.length; i++){
			assertEquals(-1, inspector.getArrayType(Inspector.ARRAY_TYPE_CODES[i].toLowerCase()));
		}
		assertEquals(-1, inspector.getArrayType("l")); //check for reference type aswell
	}
}
