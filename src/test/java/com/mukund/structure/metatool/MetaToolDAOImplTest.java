package com.mukund.structure.metatool;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mukund.structure.config.ApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class MetaToolDAOImplTest {

	private static DataSourceTransactionManager transactionManager;
	private static JdbcTemplate template;
	private static TransactionStatus status;
	private static MetaToolDAO dao;

	@Test
	public void testAddLevelD() {
		assertEquals(true, dao.addHierarchyD("TEST", "TEST"));
		assertEquals(true, dao.addLevelD("TEST","TEST", "TEST"));
		assertEquals(false, dao.addLevelD("TEST","TEST", "TEST"));
	}

	@Test
	public void testAddPropertyD() {
		assertEquals(true, dao.addPropertyD("TEST", "TEST"));
		assertEquals(false, dao.addPropertyD("TEST", "TEST"));
	}
	
	@Test
	public void testAddHeirarchyD() {
		assertEquals(true, dao.addHierarchyD("TEST", "TEST"));
		assertEquals(false, dao.addHierarchyD("TEST", "TEST"));
	}
	
	
	@Test
	public void testAddNoteTypeD() {
		assertEquals(true, dao.addNoteTypeD("GBS","TEST", "TEST"));
		assertEquals(false, dao.addNoteTypeD("GBS","TEST", "TEST"));
	}

	@Test
	public void testAddNotePropertyD() {
		assertEquals(true, dao.addNoteTypeD("GBS","TEST", "TEST"));
		assertEquals(true, dao.addPropertyD("TEST", "TEST"));
		assertEquals(true, dao.addNotePropertyD("TEST", "TEST","test"));
		assertEquals(false, dao.addNotePropertyD("TEST", "TEST","test"));
	}

	@Test
	public void testAddNoteUsageD() {
		assertEquals(true, dao.addHierarchyD("TEST", "TEST"));
		assertEquals(true, dao.addNoteTypeD("GBS","TEST", "TEST"));
		assertEquals(true, dao.addLevelD("TEST","TEST", "TEST"));
		assertEquals(true, dao.addNoteUsageD("TEST", "TEST"));
		assertEquals(false, dao.addNoteUsageD("TEST", "TEST"));
	}

	@Test
	public void testAddListTypeD() {
		assertEquals(true, dao.addListTypeD("GBS","TEST", "TEST"));
		assertEquals(false, dao.addListTypeD("GBS","TEST", "TEST"));
	}

	@Test
	public void testAddListPropertyD() {
		assertEquals(true, dao.addListTypeD("GBS","TEST", "TEST"));
		assertEquals(true, dao.addPropertyD("TEST", "TEST"));
		assertEquals(true, dao.addListPropertyD("TEST", "TEST","test"));
		assertEquals(false, dao.addListPropertyD("TEST", "TEST","test"));
	}

	@Test
	public void testAddListTypeItemD() {
		assertEquals(true, dao.addListTypeD("GBS","TEST", "TEST"));
		assertEquals(true, dao.addListTypeItemD("TEST", "TEST","test"));
		assertEquals(false, dao.addListTypeItemD("TEST", "TEST","test"));
	}

	@Test
	public void testAddListUsageD() {
		assertEquals(true, dao.addHierarchyD("TEST", "TEST"));
		assertEquals(true, dao.addListTypeD("GBS","TEST", "TEST"));
		assertEquals(true, dao.addLevelD("TEST","TEST", "TEST"));
		assertEquals(true, dao.addListUsageD("TEST", "TEST"));
		assertEquals(false, dao.addListUsageD("TEST", "TEST"));
	}

	@Test
	public void testAddFlagTypeD() {
		assertEquals(true, dao.addFlagTypeD("GBS","TEST", "TEST"));
		assertEquals(false, dao.addFlagTypeD("GBS","TEST", "TEST"));
	}

	@Test
	public void testAddFlagPropertyD() {
		assertEquals(true, dao.addFlagTypeD("GBS","TEST", "TEST"));
		assertEquals(true, dao.addPropertyD("TEST", "TEST"));
		assertEquals(true, dao.addFlagPropertyD("TEST", "TEST","test"));
		assertEquals(false, dao.addFlagPropertyD("TEST", "TEST","test"));
	}

	@Test
	public void testAddFlagUsageD() {
		assertEquals(true, dao.addHierarchyD("TEST", "TEST"));
		assertEquals(true, dao.addFlagTypeD("GBS","TEST", "TEST"));
		assertEquals(true, dao.addLevelD("TEST","TEST", "TEST"));
		assertEquals(true, dao.addFlagUsageD("TEST", "TEST"));
		assertEquals(false, dao.addFlagUsageD("TEST", "TEST"));
	}
	
	@Test
	public void testAddDroolRuleD() {
		assertEquals(true, dao.addHierarchyD("TEST", "TEST"));
		assertEquals(true, dao.addDroolRuleD("Code", "NEW", "TEST", "true"));
		assertEquals(false, dao.addDroolRuleD("Code", "NEW", "TEST", "true"));
	}
	
	@Test
	public void testAddDroolActionD() {
		assertEquals(true, dao.addDroolActionD("Code", "validate($facts)", "test.package.ClassName"));
		assertEquals(false, dao.addDroolActionD("Code", "validate($facts)", "test.package.ClassName"));
	}
	
	@Test
	public void testAddDroolRuleActionD() {
		assertEquals(true, dao.addHierarchyD("TEST", "TEST"));
		assertEquals(true, dao.addDroolRuleD("Code", "NEW", "TEST", "true"));
		assertEquals(true, dao.addDroolActionD("Code1", "validate($facts)", "test.package.ClassName"));
		assertEquals(true, dao.addDroolRuleActionD("Code","Code1"));
	}

	/**
	 * Items here are executed before the actual tests are run Note that this
	 * method gets called for each testXXX method call
	 *
	 * We are using @before annotation here as we are not extending the
	 * TestCase. If you are extending TestCase, the setUp() method with no
	 * parameter will be automatically picked up.
	 */
	@Before
	public void setUp() {
		// write setup code that must be executed before each test method run
		TransactionDefinition def = new DefaultTransactionDefinition();
		status = transactionManager.getTransaction(def);
	}

	/**
	 * Items in this method are executed after the tests are run. Note that this
	 * method gets called for each testXXX method call
	 *
	 * We are using @after annotation here as we are not extending the TestCase.
	 * If you are extending TestCase, the tearDown() method with no parameter
	 * will be automatically called after each test method run.
	 */
	@After
	public void tearDown() {
		transactionManager.rollback(status);
	}

	/**
	 * If your design has excessive coupling, you might need to run setup and
	 * tear down code once for all your tests.
	 *
	 * Excessive coupling is an indication to need for code refactoring, but if
	 * you must, then putting the @BeforeClass annotation before the method
	 * helps create one time setup. This method will be run before any of the
	 * test methods are run.
	 */
	@BeforeClass
	public static void oneTimeSetup() {
		// write your one time initialization code here
		System.out.println("@BeforeClass - One Time Setup with");
		try {
			template = getJdbcTemplate();
			dao=new MetaToolDAOImpl(template);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		transactionManager = new DataSourceTransactionManager(template.getDataSource());
	}

	/**
	 * @AfterClass annotation before the method helps create one time tear down.
	 *             This method will be run only once after all of the test
	 *             methods are run.
	 */
	@AfterClass
	public static void oneTimeTearDown() {
		System.out.println("@AfterClass - One Time Tear Down");
	}

	private static JdbcTemplate getJdbcTemplate() throws SQLException {
		JdbcTemplate template = new JdbcTemplate();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/ns");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		template.setDataSource(dataSource);
		return template;
	}
}
