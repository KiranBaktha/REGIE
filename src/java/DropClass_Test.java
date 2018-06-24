import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.sql.*;

public class DropClass_Test {
    private static Student student;
    private static Course MPCS_52011;
    private static databaseconn connector = new Connect();  // Sets database connection
    private static Connection con = connector.conn();
    private static AbstractFactory factory;

    @BeforeClass
    public static void setUp() throws Exception{
        student = new Student("Kiran Baktha",12745243); // Student who wishes to drop a class
        factory = new CourseAbstractFactory(con,"MPCS_52011");
        MPCS_52011 = factory.getClasses().get(0);
        RegisterforClass rc1 = new RegisterforClass(student,MPCS_52011,con); // Register the course to student first
        rc1.execute();
    }
    @AfterClass
    public static void tearDown() throws Exception{
        System.out.println("Test completed");
    }

    @Test
    public void test1() {
        // Test 1 Checks if student can register for class when permission required is false
       DropClass drop = new DropClass(student,MPCS_52011,con);
       int sqlUpdates = drop.execute();
       assertFalse(student.getCourses().contains(MPCS_52011)); // Ensure course not in student's course list
       assertFalse(MPCS_52011.getStudents().contains(student)); // Ensure student not in course's student list
       // Check Database
       assertEquals(1,sqlUpdates); // Ensure that one delete update was made to the database.

    }
}

