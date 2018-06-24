
/* Class Faculty represents  a faculty object in the REGIE System. Interface CommonREGIE is a common interface to be implemented by Faculty and Students.
It defines the general methods like getting name, status, departments, adding a course and viewing courses associated to either Faculty or Student.
Interface REGIE_FacultyMember states additional methods that need to be implemented by a faculty member.
* */
import java.util.*;

interface CommonREGIE{
    String getName(); // Get the name of the student or faculty member
    void setStatus(String stat); // Set status of student or faculty member (Full-Time or Part-Time)
    String getStatus(); // Get status of student or faculty member (Full-Time or Part-Time)
    List<String> getDepartments();  // Get the departments to which a faculty member belongs to
    void addCourse(Course CourseCode); // Add a course registered (Student) or course being taught (Faculty Member)
    List<String> getCourseNames();  // Get the course names of courses associated to the faculty or student

}

interface REGIE_FacultyMember{
    void setPosition(String pos); // Set the position of the faculty member(Eg: Professor, Instructor or TA)
    String getPosition(); // Get the position of the faculty member( Eg: Professor, Instructor or TA)
    void addDepartment(String department); // Add a department to which a faculty belongs (Not applicable to Students as per my design)
}



public class Faculty implements CommonREGIE,REGIE_FacultyMember{
    private String name;
    private String position ; // position of faculty (Professor, Instructor or TA)
    private String status; //status of faculty  (Full-Time or Part-Time)
    private List<String> departments = new ArrayList<String>(); // Departments to which a Faculty belongs to
    private List<Course> Courses = new ArrayList<Course>(); // Names of the courses assigned to the Faculty Member
    private int id; // ID of Faculty Member

    public Faculty(String fname, int ID){
        name = fname;
        id = ID;
    }

    public String getName(){
        return name;
    }

    public void setStatus(String stat){
        status = stat;
    }

    public String getStatus(){
        return status;
    }

    public void setPosition(String pos){
        position = pos;
    }
    public String getPosition(){
        return position;
    }

    public void addDepartment(String department){
        departments.add(department);
    }

    public List<String> getDepartments(){
        return departments;
    }

    public void addCourse(Course CourseCode){
        Courses.add(CourseCode);
    }
    public List<String> getCourseNames(){
        List<String> coursename =  new ArrayList<String>();
        for(int i =0;i<Courses.size();i++){
           coursename.add(Courses.get(i).viewName());
        }
        return coursename;
    }

}
