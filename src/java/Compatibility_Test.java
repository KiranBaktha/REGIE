/*
* Compatibility_Test class tests if the Check_Compatibility class is accurate.
* */
import static org.junit.Assert.*;
import org.junit.*;
import java.sql.*;
public class Compatibility_Test {
    private static Student s1;
    private static Course MPCS_52011;
    private static Course MPCS_51410;
    private static Course MPCS_53110;
    private static Check_Compatibility Compatibility;
    private static AbstractFactory factory;
    private static  databaseconn connector=  new Connect(); // Sets database connection
    private static Connection con = connector.conn();

    @BeforeClass
    public static void setUp() throws Exception{
        s1 = new Student("Jubil D",38957483); // Student who wishes to register
        factory = new CourseAbstractFactory(con,"MPCS_52011","MPCS_53110","MPCS_51410");
        MPCS_52011 = factory.getClasses().get(0);
        MPCS_53110 = factory.getClasses().get(1);
        MPCS_51410 = factory.getClasses().get(2);
        Compatibility = new Check_Compatibility();
    }
    @AfterClass
    public static void tearDown() throws Exception{
        System.out.println("Test completed");
    }

    @Test
    public void test1() {
        // Test 1 Checks if 2 incompatible courses are returned false
        assertFalse(Compatibility.execute(MPCS_52011.getSchedule(),MPCS_53110.getSchedule()));
    }
    @Test
    public void test2() {
        // Test 1 Checks if 2 compatible courses are returned true
        assertTrue(Compatibility.execute(MPCS_51410.getSchedule(),MPCS_52011.getSchedule()));
    }
}

