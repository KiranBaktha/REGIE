import java.util.*;

/*Class Student represents a Student object in REGIE. It implements interface CommonREGIE whose description and methods can be found
* in Faculty.java . In addition it implements interface REGIE_Student which states additional methods that need to be implemented by a student in REGIE
* to satisfy my subset requirements.*/


interface REGIE_Student{
    boolean setGrade(String coursename,String grade); // Set grade for a particular course to a student
    Map<String, String> viewGrades(); // Functionality not part of my domain so implemented as a method instead of a class here. View grades(Used for testing purposes)
    void  viewTranscript(); // View the grades in a tabular format. (Transcript) (Also not part of my domain but implemented as a method here.)
    int getID(); // returns student id
    List<Course> getCourses(); // Return the courses the student is enrolled in.
    void removeCourse(Course c);
}


public class Student implements CommonREGIE,REGIE_Student{

    private String name; // Student Name
    private String status; //status of faculty  (Full-Time or Part-Time)
    private List<Course> Courses = new ArrayList<Course>();; // Names of the courses registered by Student
    private List<String> departments = new ArrayList<String>(); // Departments to which a Student belongs to
    private Map<String, String> Course_Grade = new HashMap<String, String>(); // Stores CourseCode,Grade pair for the student
    private int id; // Student id


    public Student(String fullname,int ID) {
        name = fullname;
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
    public List<String> getDepartments(){
        return departments;
    }
    public void addCourse(Course CourseC){
        Courses.add(CourseC);
        Course_Grade.put(CourseC.getcode(),"Not Assigned"); // Add to Course Grade relationship table as well.
    }

    public List<String> getCourseNames(){
        List<String> coursename =  new ArrayList<String>();
        for(int i =0;i<Courses.size();i++){
            coursename.add(Courses.get(i).viewName());
        }
        return coursename;
    }

    public boolean setGrade(String coursecode,String grade){
        if (Course_Grade.containsKey(coursecode)){  // If course present
            Course_Grade.replace(coursecode,grade);
            return true;
        }
        else{
            System.out.println("Course " + coursecode + " Not Present For Student " + name); // Alert Faculty otherwise
            System.out.println("No updates Made.");
            return false;
        }

    }

    public Map<String, String> viewGrades(){
        return Course_Grade;
    }

    public void viewTranscript(){ // View transcript in tabular form
        System.out.println(" __________________");
        for (Map.Entry<String,String> entry: Course_Grade.entrySet()){
            System.out.println("| " + entry.getKey() + " | " + entry.getValue() + " | ");
        }
        System.out.println(" __________________");
    }

    public int getID(){
        return id;
    }

    public List<Course> getCourses(){
        return Courses;
    }

    public void removeCourse(Course c){
        Courses.remove(c);
    }

}
