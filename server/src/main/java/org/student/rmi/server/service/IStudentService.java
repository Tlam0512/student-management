package org.student.rmi.server.service;

import org.student.rmi.server.domain.Student;

import java.rmi.Remote;
import java.util.List;

public interface IStudentService extends Remote {
    List<Student> getAllStudents() throws Exception;

    void addNewStudent(Student student) throws Exception;

    void deleteStudent(String studentId) throws Exception;

    void updateStudent(Student student) throws Exception;

    List<Student> findStudent(String keyword) throws Exception;
}
