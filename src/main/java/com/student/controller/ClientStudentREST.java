package com.student.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.student.entities.Student;
import com.student.util.StudentConstants;

 
public class ClientStudentREST {
 
    public static final String SERVER_URI = "http://localhost:8080/rest-service-gradle/";
    
     
    public static void main(String args[]){
//        testGetDummyStudent();
//        System.out.println("*****");
//    	testCreateStudent();
//        System.out.println("*****");
//        testGetStudent();
//        System.out.println("*****");
        testDeleteStudent();
        System.out.println("*****");
//        testGetAllStudents();
    }
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void testGetAllStudents() {
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap> students = restTemplate.getForObject(SERVER_URI + StudentConstants.GET_ALL_STUS, List.class);
        for(LinkedHashMap map : students){
            System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));;
        }
    }
 
    /**
     * 
     */
    public static void testCreateStudent() {
        RestTemplate restTemplate = new RestTemplate();
        Student student = new Student();
        student.setStudentName("Fernando Villar");
        student.setCourse("AAA");
        student.setRollNumber(5);
        Student response = restTemplate.postForObject(SERVER_URI + StudentConstants.CREATE_STUDENT, student, Student.class);
        printStudentData(response);
    }
 
    public static void testGetStudent() {
        RestTemplate restTemplate = new RestTemplate();
        int id = 1;
        Student student = restTemplate.getForObject(SERVER_URI + "/rest/student/"+id, Student.class);
        printStudentData(student);
    }
    
    public static void testDeleteStudent() {
        RestTemplate restTemplate = new RestTemplate();
        int id = 1;
        String student = restTemplate.postForObject(SERVER_URI + "rest/student/delete", id, String.class);
        System.out.println(student);
    }
 
    public static void testGetDummyStudent() {
        RestTemplate restTemplate = new RestTemplate();
        Student student = restTemplate.getForObject(SERVER_URI + StudentConstants.DUMMY_STUDENT, Student.class);
        printStudentData(student);
    }
     
    public static void printStudentData(Student student){
        System.out.println("ID=" + student.getId() + ", Name=" + student.getStudentName());
    }
}