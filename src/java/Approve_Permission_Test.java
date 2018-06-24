import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class Approve_Permission_Test {
  private static  Student Luke;
  private static Student Jason;
  private static Course MPCS_51410;
  private static  Faculty Mark;
  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private static databaseconn Connector = new Connect();  // Sets database connection
    private  static Connection Con = Connector.conn();
    private static AbstractFactory factory;

    @BeforeClass
    public static  void setUp() throws Exception{

        Luke = new Student ("Luke Harington",48563547);
        Jason = new Student("Jason Finley",12734234);
        factory = new CourseAbstractFactory(Con,"MPCS_51410");
        MPCS_51410 = factory.getClasses().get(0);
        MPCS_51410.add_PendingStudent(Luke); // Add student's request to class
        Mark = new Faculty("Mark Shacklette",46363243);
        Mark.addCourse(MPCS_51410); // Add Object Oriented Programming to Mark's list
        System.setOut(new PrintStream(outContent)); // Set up to read printed message
        // Add students request to database
        int rs=0;
        try {   // Update database
            Statement stmt=Con.createStatement();
            rs=stmt.executeUpdate("insert into pending_requests values('" + MPCS_51410.getcode()  +"'," + String.valueOf(Luke.getID()) + ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() throws Exception{ // Reset the database to how it was before the two tests
        System.out.println("Test completed");
        int rs=0;
        try {   // Update database (Removes test modifications made)
            Statement stmt2=Con.createStatement();  // Removes request created
            rs=stmt2.executeUpdate("delete from  pending_requests where course_code = '" + MPCS_51410.getcode()  +"' and StudentID = " + String.valueOf(Luke.getID()) + ";");
            Statement stmt3=Con.createStatement(); // Removes course added
            rs=stmt2.executeUpdate("delete from  Course_Student where course_code = '" + MPCS_51410.getcode()  +"' and StudentID = " + String.valueOf(Luke.getID()) + ";");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        // Test 1 (Checks if students permission can be approved accurately)
        Approve_Permission ap1 = new Approve_Permission(Mark,Luke,MPCS_51410,Con);
        int sqlUpdates = ap1.execute();
        assertTrue(Luke.getCourseNames().contains(MPCS_51410.viewName())); // Assert course added to student
        assertTrue(MPCS_51410.getStudents().contains(Luke)); // Assert student added to course
        assertEquals(1,sqlUpdates); // Assert only 1 update was made.

    }

    @Test
    public void test2(){
        // Test 2 (Checks if faculty cannot approve student permission if student has not requested for the course or faculty is not teaching the course.
        Approve_Permission ap2 = new Approve_Permission(Mark,Jason,MPCS_51410,Con); // Jason has not requested for the course.
        int sqlUpdates = ap2.execute();
        // Ensure Mark is alerted because he is not teaching Foundations of Data Analysis
        assertEquals("Either Faculty not teaching the course or Student not requested for the course.".replaceAll("\n","").replaceAll("\r",""),outContent.toString().replaceAll("\n","").replaceAll("\r",""));
        assertEquals(0,sqlUpdates);
    }
}
