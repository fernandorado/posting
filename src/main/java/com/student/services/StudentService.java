package com.student.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.student.entities.Student;
import com.student.repositories.StudentRepository;

@Service("serviceStudent")
public class StudentService {
	
	@Autowired
	StudentRepository studentRepository;

	@Transactional
	public List<Student> getAllStudents() {
		return this.studentRepository.findAll();
	}

	@Transactional
	public Student createStudent(Student student) {
		return this.studentRepository.save(student);
	}
	
	@Transactional
	public Student deleteStudent(long id) {
		Student student = this.studentRepository.findOne(id);
		this.studentRepository.delete(student);
		return  student;
	}
	
	@Transactional
	public Student getStudent(long id) {
		return this.studentRepository.findOne(id);
	}
}
