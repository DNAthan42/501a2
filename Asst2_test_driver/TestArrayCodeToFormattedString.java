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
		assertEquals("", inspector.arrayCodeToFormattedString("Invalid"));
	}
}
