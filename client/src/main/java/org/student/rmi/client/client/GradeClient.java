package org.student.rmi.client.client;

import org.student.rmi.client.util.ConnectServer;
import org.student.rmi.server.domain.Grade;
import org.student.rmi.server.service.IGradeService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GradeClient {
    private final IGradeService gradeService;

    public GradeClient() {
        try {
            gradeService = (IGradeService) ConnectServer.getInstance().getRegistry().lookup("gradeService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Grade> getGradeByStudentId(String studentId) throws Exception {
        return gradeService.getGradeByStudentId(studentId);
    }

    public List<String> getGradeNotInStudentId(String studentId) throws Exception {
        return gradeService.getGradeNotInStudentId(studentId);
    }

    public void addGradeByStudentId(Grade grade) throws Exception {
        gradeService.addGradeByStudentId(grade);
    }

    public void updateGradeByStudentId(Grade grade) throws Exception {
        gradeService.updateGradeByStudentId(grade);
    }

    public void deleteGradeByStudentId(Grade grade) throws Exception {
        gradeService.deleteGradeByStudentId(grade);
    }

    public void addNewSubject(Grade grade, Integer credit) throws Exception {
        gradeService.addNewSubject(grade, credit);
    }




}
