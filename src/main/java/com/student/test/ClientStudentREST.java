package com.student.test;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.student.entities.Student;
import com.student.util.StudentConstants;
	

public class ClientStudentREST {
 
    public static final String SERVER_URI = "http://localhost:8080/";
    
    private static RestTemplate restTemplate = new RestTemplate();
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
	public void testGetAllStudents() {
        List<LinkedHashMap> students = restTemplate.getForObject(SERVER_URI + StudentConstants.GET_ALL_STUS, List.class);
        for(LinkedHashMap map : students){
            System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));;
        }
    }
    
    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setStudentName("Fernando Villar");
        student.setCourse("AAA");
        student.setRollNumber(5);
        Student response = restTemplate.postForObject(SERVER_URI + StudentConstants.CREATE_STUDENT, student, Student.class);
        printStudentData(response);
    }
 
    @Test
    public void testGetStudent() {
        int id = 5;
        Student student = restTemplate.getForObject(SERVER_URI + "/rest/student?id="+id, Student.class);
        printStudentData(student);
    }
    
    @Test
    public void testDeleteStudent() {
        int id = 4;
        String student = restTemplate.postForObject(SERVER_URI + "rest/student/delete?id="+id, id, String.class);
        System.out.println(student);
    }
 
    @Test
    public void testGetDummyStudent() {
        Student student = restTemplate.getForObject(SERVER_URI + StudentConstants.DUMMY_STUDENT, Student.class);
        printStudentData(student);
    }
     
    public static void printStudentData(Student student){
        System.out.println("ID=" + student.getId() + ", Name=" + student.getStudentName());
    }
}