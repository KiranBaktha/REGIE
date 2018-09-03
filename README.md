# REGIE
Course Registration System as part of coursework in MPCS 51410 - Object Oriented Programming 

## Project Description

This project deliverable contains a subset of the [REGIE Course Registration System](http://www.kiranbaktha.com/51410_ProjectDescription.html). It covers functionalities of a faculty actor as well as course registration functionality of a student actor. For each functionality, there is a separate test file that tests that functionality in depth using JUnit Tests.  


## Individual Java Source File Description (Arranged in Alphabetical Order of File Name)

1. **AbstractFactory.java** -> Factory Class that follows the Abstract Factory Pattern to create 
course objects needed for running tests.

2. **Approve_Permission.java** -> Allows Faculty to grant permission to students in courses which require Instructors Approval.

3. **Approve_Permission_Test.java** -> Tests the working of Approve_Permission.java. 

4. **assign_grade.java** -> Allows Faculty to assign grade to a student or a list of students in a course.

5. **assign_grade_test.java** -> Tests the working of assign_grade.java

6. **Check_Compatibility.java**-> Checks if 2 courses are compatible with each other in the sense that a student can register for both the courses without any schedule clash.

7. **Compatibility_Test.java** -> Tests the working of Check_Compatibility.java.

8. **Connect.java** -> Establishes a Database Connection to the MySQL database.

9. **Course.java** -> Represents a Course in REGIE. (Entity in Domain Driven Design).

10. **CourseContext.java** -> Creates a context and the states a course can be in. (Refer to State Pattern in this readme  for more details)

11. **CourseStatus_Test.java** -> Tests the working of CourseContext.java. 

12. **DropClass.java** -> Allows a student to drop a course from his timetable.

13. **DropClass_Test.java** -> Tests the working of DropClass.java.

14. **Faculty.java** -> Represents a Faculty in REGIE. (Entity in Domain Driven Design)

15. **Grades.java** -> Represents the allowable grades that a faculty can assign to a student in REGIE. (Value Object in Domain Driven Design)

16. **Modify_Features.java** -> Allows Faculty to add or delete features to a course.

17. **Modify_Features_test.java** -> Tests the working of Modify_Features.java. 

18. **Modify_grade.java** -> Allows Faculty to Modify grade for a student or a list of students.

19. **Modify_grade_test.java** -> Tests the working of Modify_grade.java.

20. **Observer.java** -> An observer that establishes an interface and creates a concrete observer. (Refer to Observer Pattern in this readme for more details.)

21. **RegisterforClass.java** -> Allows a student to register for a course in REGIE.

22. **Register_Class_Test.java** -> Tests the working of RegisterforClass.java.

23. **Student.java** -> Represents a Student in REGIE. (Entity in Domain Driven Design).

24. **View_Students.java** -> Allows Faculty to view students in a course he is teaching.

25. **View_Students_Test.java** -> Tests the working of View_Students.java.  

## Use of SOLID in Design

- **Single Responsibility Principle:** <p align="justify">Each class has a Single Responsibility which is described by their class name. They also serve only 1 master and only have 1 reason to change (need to be recreated). </p>
- **Interface Segregation Principle:** <p align="justify">Interface for Student and Faculty entity objects have been split into 2 interfaces (CommonREGIE interface which is common to both and REGIE_FacultyMember interface for Faculty members and REGIE_Student interface for Students). This way Student and Faculty are not dependent on the methods they do not use.</p>
- **Dependency Inversion Principle:** <p align="justify">High Level Modules which are the test and functionality modules are not dependent on concrete Database Connection like MySQL Connection. They are dependent on an abstract Database Connection which is implemented by Connect.java. This way, if we want to change our database to NoSQL for example, we can do so without any hassle.</p>
- **Open/Closed Principle:** <p align="justify">By defining clear abstractions for entity objects Student, Course and Faculty, in the domain of a Course Registration System, these entities can be easily extended to include additional methods for other entity objects if needed. It is closed for modification in the sense that we need not change it in order to extended it.</p>

## Use of OO Patterns in Design

-	**Abstract Factory:** <p align="justify">AbstractFactory.java creates Course objects based on their details in the course database. All the test cases use Abstract Factory to create a set of course objects that will be used in the test. This way the test class is not aware of the details required for the instantiation of course objects and adds convenience to code structure by not having to repeat the constructor parameters again.</p>
- **Observer:** <p align="justify">Observer.java sets the interface for an observer and create a concrete Observer called Permission_Modifier_Observer. The main idea is that since faculties can add features to a course, when the feature is “Instructor Approval Required”, we want our system to stop adding students to the course and add students to the pending requests list which the faculty can later access to grant permission to students. Similarly, when the faculty deletes the feature “Instructor Approval Required”, we want students to be added to the course directly and not to the pending requests list. To implement this feature, Modify_Features.java which allows features to be added /deleted is the subject that asks its observer (Permission_Modifier_Observer) to update the course when the feature being modified is “Instructor Approval Required”. </p>
- **State:** <p align="justify">We want the course to have 2 states. Course Open state is when the number of students in the course is less than 60 and allows students to register to the course. Course Closed state is when the number of students is 60 and students can only be added to the waitlist for the course. To implement this feature, State Pattern has been used and CourseContext.java creates the necessary states and Context as per the pattern design.</p>
- **Singleton:** <p align="justify">The database connection has been implemented using the Singleton pattern. At a given point only 1 instance of the Database connection exists. All test classes create an instance of Database Connection and pass that instance to the dependent sub-classes and do not let the sub-classes create their own instance of Database Connection. This way, only 1 class can modify the database at a given execution.</p>
- **Composite:** <p align="justify">This pattern allows a faculty to assign/modify grade to a single student (leaf object) or a group of students (composite object). This feature would come in handy if a project was completed by a group and the faculty wants to assign the same grade to the whole group. He can conveniently assign grade to the whole group in a single method call by the system. (assign_grade.java and Modify_grade.java have implementations of this pattern). </p>

## Db Code

1. **Create_DB.sql** -> Bunch of MySQL statements that create the database.

2. **Populate_DB.sql** -> Bunch of MySQL statements that populate the database.




