import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Column_Test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createColumnWithName() {
		Column column1 = new Column("Todo");
		assertEquals("Column Name must be Todo", column1.getName(), "Todo");
	}
	@Test
	public void changeName() {
		Column column1 = new Column("Todo");
		assertEquals("Column Name must be Todo", column1.getName(), "Todo");
		column1.setName("Doing");
		assertEquals("Column Name must be Doing", column1.getName(), "Doing");
	}	

}
