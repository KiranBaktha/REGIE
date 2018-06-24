/*This is the populate script where the tables in the database are populated.*/
/*Populate Course_Database table.*/
insert into Course values('MPCS_51410','Object Oriented Programming','Ryerson-271','Tuesday 17:30-20:30',1);
insert into Course values('MPCS_52011','Introduction to Computer Systems','Ryerson-251','Monday 17:30-20:30',0);
insert into Course values('MPCS_51200','Introduction to Software Engineering','Young-302','Wednesday 17:30-20:30',1);
insert into Course values('MPCS_53110','Foundations of Computational Data Analysis','Ryerson-251','Monday 17:30-20:30',0);
insert into Course values('MPCS_52553','Web Development','Young-302','Saturday 17:30-20:30',0);
/*Populate Course_FacultyMember table*/
insert into Course_FacultyMember values('MPCS_51410',34342);
insert into Course_FacultyMember values('MPCS_51410',12323);
insert into Course_FacultyMember values('MPCS_51410',67544);
insert into Course_FacultyMember values('MPCS_52011',12632);
insert into Course_FacultyMember values('MPCS_52011',43642);
insert into Course_FacultyMember values('MPCS_51200',24574);
insert into Course_FacultyMember values('MPCS_51200',34342);
insert into Course_FacultyMember values('MPCS_53110',34536);
insert into Course_FacultyMember values('MPCS_52553',23527);
insert into Course_FacultyMember values('MPCS_52553',86434);
/*Populate Faculty_Database table*/
insert into Faculty values(34342,'uchicago_f','Mark Shacklette','Adjunct Professor',7284372832,'Chicago,IL');
insert into Faculty values(12323,'uchicago_f','Paul Bossi','TA',5838293746,'Chicago,IL');
insert into Faculty values(67544,'uchicago_f','John Hadidian','TA',3472897532,'Chicago,IL');
insert into Faculty values(12632,'uchicago_f','Kevin Marcus','Lecturer',9894643745,'Chicago,IL');
insert into Faculty values(43642,'uchicago_f','Ali Hong','TA',2384949375,'Chicago,IL');
insert into Faculty values(24574,'uchicago_f','Peter Vassilos','Lecturer',6638426483,'Chicago,IL');
insert into Faculty values(34536,'uchicago_f','Amitabh C','Associate Clinical Professor',3523487553,'Chicago,IL');
insert into Faculty values(23527,'uchicago_f','Jeffrey Cohen','Adjunct Assistant Professor',9735728365,'Chicago,IL');
insert into Faculty values(86434,'uchicago_f','Jason Mark','TA',4345838475,'Chicago,IL');
/*Populate Student_Database table*/
insert into Student values(12745243,'uchicago_s','Kiran Baktha',7875607532,'Chicago,IL');
insert into Student values(15683472,'uchicago_s','Dipanshu Sehjal',8763275832,'Chicago,IL');
insert into Student values(73795324,'uchicago_s','Jae H',3583962843,'Chicago,IL');
insert into Student values(15683482,'uchicago_s','Jake George',7385730273,'Chicago,IL');
insert into Student values(38957483,'uchicago_s','Jubil D',4574569374,'Chicago,IL');
insert into Student values(73639574,'uchicago_s','Jacob Mesa',6374695734,'Chicago,IL');
insert into Student values(24375345,'uchicago_s','Amanda Generes',8653475843,'Chicago,IL');
insert into Student values(67457383,'uchicago_s','Sophia K',75964583649,'Chicago,IL');
insert into Student values(97643646,'uchicago_s','Oliver Montana',6583846857,'Chicago,IL');
insert into Student values(48563547,'uchicago_s','Luke Harington',2345775864,'Chicago,IL');

/*Populate Course_Student table*/
insert into Course_Student values('MPCS_51410',12745243,null);
insert into Course_Student values('MPCS_51410',15683472,null);
insert into Course_Student values('MPCS_53110',73795324,null);
insert into Course_Student values('MPCS_52011',15683482,null);
insert into Course_Student values('MPCS_52553',38957483,null);
insert into Course_Student values('MPCS_52011',73639574,null);
insert into Course_Student values('MPCS_51200',15683472,null);
insert into Course_Student values('MPCS_53110',24375345,null);
insert into Course_Student values('MPCS_52553',67457383,null);
insert into Course_Student values('MPCS_51200',97643646,null);
insert into Course_Student values('MPCS_51200',48563547,null);

/*Populate Administrator_Database table*/
insert into Administrator_Database values(78684435,'Molly Stoner',7576347532,'Chicago,IL');
insert into Administrator_Database values(89563753,'Amanda Taylor',6547375834,'Chicago,IL');

/*Populate Pending_Requests table*/
insert into pending_requests values('MPCS_52553',12745243);
insert into pending_requests values('MPCS_52553',73639574);
insert into pending_requests values('MPCS_53110',73639574);

/*Populate Course Features table*/
insert into Course_Features values('MPCS_51410','Project-Based');
insert into Course_Features values('MPCS_51200','Digital Lectures');
insert into Course_Features values('MPCS_53110','Digital Lectures');
insert into Course_Features values('MPCS_51200','Lab-Only');
insert into Course_Features values('MPCS_51410','Audit not allowed');
insert into Course_Features values('MPCS_52553','Undergraduates Only');
insert into Course_Features values('MPCS_51410','Instructor Approval Required');
insert into Course_Features values('MPCS_51200','Instructor Approval Required');






