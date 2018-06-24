import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class View_Students_Test {
    private Student Kiran;
    private Student Dipan;
    private Course MPCS_51410;
    private Course MPCS_53110;
    private Faculty Mark;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private databaseconn connector = new Connect();// Sets database connection
    private Connection con = connector.conn();
    private AbstractFactory factory; // Factory Creater

    @Before
    public void setUp() throws Exception{
        Kiran = new Student ("Kiran Baktha",12745243); //Students in Object Oriented Programming
        Dipan = new Student("Dipanshu Sehjal",15683472);
        factory = new CourseAbstractFactory(con,"MPCS_51410","MPCS_53110");
        MPCS_51410 = factory.getClasses().get(0);
        MPCS_53110 = factory.getClasses().get(1);
        MPCS_51410.addStudent(Kiran); // Add students to class
        MPCS_51410.addStudent(Dipan);
        Mark = new Faculty("Mark Shacklette",46363243);
        Mark.addCourse(MPCS_51410); // Add Object Oriented Programming to Mark's list
        System.setOut(new PrintStream(outContent)); // Set up to read printed message
    }
    @After
    public void tearDown() throws Exception{
        System.out.println("Test completed");
    }
    @Test
    public void test1() {
        // Test 1 (Checks if students can be viewed accurately)
        View_Students vs1 = new View_Students(MPCS_51410, Mark);
        List<Integer> result = vs1.execute();
        assertEquals(2, result.size()); // Assert that only 2 students in class
        assertTrue(result.contains(12745243)); // Assert Kiran Baktha is present
        assertTrue(result.contains(15683472)); // Assert Jason Finley is present
        List<Integer> StudentsFromDatabase = new ArrayList<Integer>();
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select StudentID from Course_Student where course_code = '" + "MPCS_51410" + "';");
            while(rs.next()){
                StudentsFromDatabase.add(rs.getInt(1)); // Add each student ID from database
            }
        }catch (Exception e){
            System.out.println(e);
        }
       assertEquals(result,StudentsFromDatabase);
    }

    @Test
    public void test2(){
        // Test 2 (Checks if faculty cannot view students if he is not associated to that class)
        View_Students vs2 = new View_Students(MPCS_53110,Mark);
        vs2.execute();
        // Ensure Mark is alerted because he is not teaching Foundations of Data Analysis
        assertEquals("Faculty Mark Shacklette is not teaching the course Foundations of Computational Data Analysis".replaceAll("\n","").replaceAll("\r",""),outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }
}