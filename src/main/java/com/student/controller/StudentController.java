package com.student.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    
    @RequestMapping(value = StudentConstants.DUMMY_STUDENT, method = RequestMethod.GET, produces="application/json" )
    public @ResponseBody Student getDummyStudent() {
        logger.info("Start getDummyStudent but this student is not in the Database");
        Student student = new Student();
        student.setId(9999);
        student.setStudentName("Dummy");
        return student;
    }
     
    @RequestMapping(value = StudentConstants.GET_ALL_STUS, method = RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Student> getAllStudents() {
        logger.info("Start getAllStudents.");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();  
		Session session = sessionFactory.openSession();  
		session.beginTransaction();  
		Query query = session.createQuery("from Student");  
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();  
		for(Student student : students) {  
			System.out.println("Roll Number: "+student.getRollNumber()+", Student Name: "+student.getStudentName()+", Course: "+student.getCourse());  
		}
		session.getTransaction().commit();  
		sessionFactory.close(); 
        return students;
    }
     
    @RequestMapping(value = StudentConstants.CREATE_STUDENT, method = RequestMethod.POST, produces="application/json")
    public @ResponseBody Student createStudent(@RequestBody Student student) {
        logger.info("Start createStudent.");
        System.out.println("Data Received: " + student.toString());
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();  
		Session session = sessionFactory.openSession();  
		session.beginTransaction();
		session.save(student);
		session.getTransaction().commit();  
		sessionFactory.close(); 
        return student;
    }
    
    @RequestMapping(value = StudentConstants.GET_STUDENT, method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Student getStudent(@PathVariable("id") int idStudent) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();  
		Session session = sessionFactory.openSession();  
		session.beginTransaction();
		System.out.println("Data Received: " + idStudent);
		Student currentStudent = (Student)session.get(Student.class, idStudent); 
		System.out.println("Data Updated: " + currentStudent.toString());
		sessionFactory.close(); 
		return currentStudent;
	}
     
    @RequestMapping(value = StudentConstants.DELETE_STUDENT, method = RequestMethod.POST, produces="application/json")
    public @ResponseBody String deleteStudent(@RequestBody int idStudent) {
        logger.info("Start deleteStudent.");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();  
		Session session = sessionFactory.openSession();  
		session.beginTransaction();
		System.out.println("Data Received: " + idStudent);
		Query q = session.createQuery("delete Student where id = " + idStudent);
		int result = q.executeUpdate();
		System.out.println("Data Deleted: " + result);

		session.getTransaction().commit();  
		sessionFactory.close();
        return "The Student with id:"+ idStudent +" was removed";
    }
}