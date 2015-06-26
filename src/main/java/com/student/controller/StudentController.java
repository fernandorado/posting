package com.student.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.student.entities.Student;
import com.student.services.StudentService;
import com.student.util.StudentConstants;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    StudentService studentService;
    
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
        logger.info("Get All.");
		List<Student> students = studentService.getAllStudents();  
		for(Student student : students) {  
			System.out.println("Roll Number: "+student.getRollNumber()+", Student Name: "+student.getStudentName()+", Course: "+student.getCourse());  
		}
        return students;
    }

    @RequestMapping(value = StudentConstants.CREATE_STUDENT, method = RequestMethod.POST, produces="application/json")
    public @ResponseBody Student createStudent(@RequestBody Student student) {
        logger.info("Create by"+ student);
        studentService.createStudent(student);
        return student;
    }
    
    @RequestMapping(value = StudentConstants.GET_STUDENT, method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Student getStudent(@RequestParam(value="id") int idStudent) {
		System.out.println("Get by id: " + idStudent);
		Student currentStudent = studentService.getStudent(idStudent);
		return currentStudent;
	}
     
    @RequestMapping(value = StudentConstants.DELETE_STUDENT, method = RequestMethod.POST, produces="application/json")
    public @ResponseBody String deleteStudent(@RequestBody int idStudent) {
        logger.info("Delete by id "+ idStudent);
        studentService.deleteStudent(idStudent);
        return "The Student with id:"+ idStudent +" was removed";
    }
}