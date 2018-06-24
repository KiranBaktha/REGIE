/*This is the create script where only tables are created in the database.  */
create database if not exists kiranDB ;
use kiranDB;
/* Course_Database table would store the course_code(primary key) along with the course name,venue and time*/
drop table if exists Course;
create table Course(course_code varchar(12) primary key,
                    course_name varchar(80), 
					venue varchar(20), 
                    course_time varchar(30), 
                    permission_required boolean);
/*Course_FacultyMember table would store the faculty members (Professors/Instructors/TAs) of a course. Since there can be multiple faculty
members for a course, the primary key is a combination of them.*/
drop table if exists Course_FacultyMember;
create table Course_FacultyMember(course_code varchar(12), 
								  FacultyID int(5), 
                                  primary key(course_code,FacultyID));
/* Student_Database table stores the Student details. Each student has a unique student ID which is used as the primary key.*/
drop table if exists Student;
create table Student(StudentID int(8) primary key, 
                     password varchar(20),
                     s_name varchar(15), 
                     phone_no bigint(10), 
                     address varchar(30));
/* Faculty_Database table stores the FacultyMember details. Each member has a unique faculty ID which is used as the primary key.*/
drop table if exists Faculty;
create table Faculty(FacultyID int(5) primary key, 
					 password varchar(20),
					 f_name varchar(15),
                     position varchar(30), 
                     phone_no bigint(10), 
                     address varchar(30));
/*Course_Student table stores the students in each course with their grade (Initially null). Since each course has multiple students, 
primary key is a combination of student id and course code.*/
drop table if exists Course_Student;
create table Course_Student(course_code varchar(12),
                            StudentID int(8), 
                            grade varchar(2) default null,
                            primary key(course_code,StudentID));
/*Administrator_Database stores the Administrator details. Each admin has a unique AdminID used as the primary key.*/
drop table if exists Administrator_Database;
create table Administrator_Database(AdminID int(8) primary key, 
                                    a_name varchar(15), 
                                    phone_no bigint(10), 
                                    address varchar(30));
/*View the tables*/
/*Pending_Requests stores the pending requests of students for instructor approval required courses. Both student id and course code are the primary keys.*/
drop table if exists Pending_Requests;
create table Pending_Requests(course_code varchar(12), 
                              StudentID int(8),
                              primary key(course_code,StudentID));
/*Course_Features stores the features of a course. Since a course can have multiple features, the primary key is both the
course_code and the feature.*/
drop table if exists Course_Features;
create table Course_Features(course_code varchar(12), 
                             feature varchar(30),
                             primary key(course_code,feature));
                             
                             
/*Waitlisted_Students table stores the students who are waitlised. This happens when a student registers for a course that is
already full. This table is not populated initially because no course is full initially.*/                             
drop table if exists Waitlisted_Students;
create table Waitlisted_Students(course_code varchar(12),
								 StudentID int (8),
                                 primary key(course_code,StudentID));
show tables;

