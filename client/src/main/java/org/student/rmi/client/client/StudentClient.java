package org.student.rmi.client.client;

import org.student.rmi.client.util.ConnectServer;
import org.student.rmi.server.domain.Student;
import org.student.rmi.server.service.IAccountService;
import org.student.rmi.server.service.IStudentService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;

public class StudentClient {
    private final IStudentService studentService;

    public StudentClient()  {
        try {
            studentService = (IStudentService) ConnectServer.getInstance()
                    .getRegistry()
                    .lookup("studentService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getAllStudent() throws Exception {
        return studentService.getAllStudents();
    }

    public void addNewStudent(Student student) throws Exception {
        studentService.addNewStudent(student);
    }

    public void updateStudent(Student studentUpdate) throws Exception {
        studentService.updateStudent(studentUpdate);
    }

    public void deleteStudent(String id) throws Exception {
        studentService.deleteStudent(id);
    }

    public List<Student> findStudent(String keyword) throws Exception {
        return studentService.findStudent(keyword);
    }


}
