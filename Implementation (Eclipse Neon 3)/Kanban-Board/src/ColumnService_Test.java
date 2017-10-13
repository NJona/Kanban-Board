import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ColumnService_Test {

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
	public void testMethodGetColumns() {
		ColumnService columnService = new ColumnService();
		
		columnService.createColumnArray(3);		
		columnService.addColumnToArray(0, "Todo");
		columnService.addColumnToArray(1, "Doing");
		columnService.addColumnToArray(2, "Done");
		
		Column []columnArray = columnService.getColumns();
		
		assertEquals("Column Name1 must be Todo", columnArray[0].getName(), "Todo");
		assertEquals("Column Name2 must be Doing", columnArray[1].getName(), "Doing");
		assertEquals("Column Name3 must be Done", columnArray[2].getName(), "Done");
	}

}
