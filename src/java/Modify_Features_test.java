import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class Modify_Features_test {
    private static Course MPCS_52011;
    private static Course MPCS_51410;
    private static Faculty Mark;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static databaseconn connector = new Connect();  // Sets database connection
    private static Connection con = connector.conn();
    private static AbstractFactory factory; // Factory Creater


    @BeforeClass
    public static void setUp() throws Exception{
        factory = new CourseAbstractFactory(con,"MPCS_51410","MPCS_52011");
        MPCS_51410 = factory.getClasses().get(0);
        MPCS_52011 = factory.getClasses().get(1);
        MPCS_51410.addFeature("Instructor Approval Required");
        MPCS_51410.addFeature("Project-Based");
        Mark = new Faculty("Mark Shacklette",46363243);
        MPCS_51410.addInstructor(Mark);
        Mark.addCourse(MPCS_51410);
        System.setOut(new PrintStream(outContent)); // Set up to read printed message
    }
    @AfterClass
    public static void tearDown() throws Exception{
        System.out.println("Test completed");
        //Reset the database to it's previous state before the tests
       try {
           Statement stmt2 = con.createStatement();
           stmt2.executeUpdate("delete from Course_Features where course_code = '" + MPCS_52011.getcode() + "' and feature = 'Instructor Approval Required'"); // Remove the added feature (Test3)
           stmt2.executeUpdate("update Course set permission_required = 0 where course_code = '" + MPCS_52011.getcode() + "';"); // Reset permission (Test3)
           stmt2.executeUpdate("update Course set permission_required = 1 where course_code = '" + MPCS_51410.getcode() + "';"); // Set permission back (Test4)
           stmt2.executeUpdate("insert into Course_Features values('" + MPCS_51410.getcode() + "','Instructor Approval Required');"); // Add the removed feature (Test4)
       } catch(Exception e){
           System.out.println(e);
        }
    }
    @Test
    public void test1(){
        // Test 1 Checks if Faculty cannot add features to the course if he is not an instructor for the course.
        Modify_Features mf = new Modify_Features(MPCS_52011,Mark,"Instructor Approval Required",con);
        int updates = mf.executeAdd();
        assertEquals(0,updates); // Ensure no updates were made
        // Ensure accurate error message
        assertEquals("Faculty Mark Shacklette is not teaching the course Introduction to Computer Systems".replaceAll("\n","").replaceAll("\r",""),outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }
    @Test
    public void test2(){
        // Test 2 Checks if an already present feature is not added to the course
        Modify_Features mf = new Modify_Features(MPCS_51410,Mark,"Project-Based",con);
        int updates = mf.executeAdd();
        assertEquals(0,updates); // Ensure no updates were made
        // Ensure accurate error message. Note the first message is included as a check because the reader reads the entire output message.
        assertEquals("Faculty Mark Shacklette is not teaching the course Introduction to Computer SystemsFeature already present!".replaceAll("\n","").replaceAll("\r",""),outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }

    @Test
    public void test3() {
        // Test 3 checks for 2 things. If a course feature can be added accurately and if it is 'Instructor Approval Required', does the observer get updated.
        MPCS_52011.addInstructor(Mark); // Add introduction to computer systems to Mark's course list for testing purposes.
        Mark.addCourse(MPCS_52011);
        Modify_Features mf = new Modify_Features(MPCS_52011,Mark,"Instructor Approval Required",con);
        int sqlUpdates = mf.executeAdd(); // Add feature
        assertEquals(2,sqlUpdates); // Ensure 2 updates were made...1 by the subject and 1 by the observer
        List<String> course_features= new ArrayList<String>();
        try { // Check database
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select feature from Course_Features where course_code = '" + String.valueOf(MPCS_52011.getcode())+"';");
            while(rs.next()){
                course_features.add(rs.getString(1)); // Add features of course in Array list
            }
            ResultSet rs2=stmt.executeQuery("select permission_required from Course where course_code = '" + String.valueOf(MPCS_52011.getcode())+"';");
            while(rs2.next()){
                assertEquals(1,rs2.getInt(1)); // Check if the permission was set by the observer
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        assertTrue(course_features.contains("Instructor Approval Required")); // Check if feature was successfully added
    }

    @Test
    public void test4(){
        // Test 4 checks for 2 things. If a course feature can be deleted accurately and if it is 'Instructor Approval Required', does the observer get updated.
        Modify_Features mf = new Modify_Features(MPCS_51410,Mark,"Instructor Approval Required",con);
        int sqlUpdates = mf.executeDelete(); // Delete Feature
        assertEquals(2,sqlUpdates); // Ensure 2 updates were made...1 by the subject and 1 by the observer
        List<String> course_features= new ArrayList<String>();
        try { // Check database
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select feature from Course_Features where course_code = '" + String.valueOf(MPCS_51410.getcode())+"';");
            while(rs.next()){
                course_features.add(rs.getString(1)); // Add features of course in Array list
            }
            ResultSet rs2=stmt.executeQuery("select permission_required from Course where course_code = '" + String.valueOf(MPCS_51410.getcode())+"';");
            while(rs2.next()){
                assertEquals(0,rs2.getInt(1)); // Check if the permission was reset by the observer
            }
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        assertFalse(course_features.contains("Instructor Approval Required")); // Check if feature was successfully deleted

    }

}

