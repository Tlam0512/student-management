package org.student.rmi.server.service;

import org.student.rmi.server.domain.Grade;

import java.rmi.Remote;
import java.util.List;

public interface IGradeService extends Remote {
    List<Grade> getGradeByStudentId(String studentId) throws Exception;

    List<String> getGradeNotInStudentId(String studentId) throws Exception;

    void addGradeByStudentId(Grade grade) throws Exception;

    void updateGradeByStudentId(Grade grade) throws Exception;

    void deleteGradeByStudentId(Grade grade) throws Exception;

    void addNewSubject(Grade grade, Integer credit) throws Exception;
}
