package org.student.rmi.server.service.impl;

import org.student.rmi.server.domain.Grade;
import org.student.rmi.server.service.IGradeService;
import org.student.rmi.server.util.DbConnect;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GradeServiceImpl extends UnicastRemoteObject
        implements IGradeService {

    private final DbConnect dbConnect = DbConnect.getInstance();

    public GradeServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Grade> getGradeByStudentId(String studentId) {
        final String sql = """
                select g.*, s.full_name, sj.subject_name from grades g
                join subjects sj on sj.subject_id = g.subject_id
                join students s on g.student_id = s.student_id 
                where s.student_id = ?
                """;

        return dbConnect.executeQuery(sql, studentId).stream()
                .map(Grade::mapToGrade)
                .toList();
    }

    @Override
    public List<String> getGradeNotInStudentId(String studentId) {
        final String sql = """
                SELECT s.subject_id, s.subject_name
                FROM subjects s
                         LEFT JOIN grades g ON s.subject_id = g.subject_id AND g.student_id = ?
                WHERE g.subject_id IS NULL;
                """;

        return dbConnect.executeQuery(sql, studentId).stream()
                .map(objects -> String.format("%d - %s", (Integer) objects[0], (String) objects[1]))
                .toList();
    }

    @Override
    public void addGradeByStudentId(Grade grade) {
        final String sql = """
                insert into Grades(student_id, subject_id, attendance_score, midterm_score, final_score)
                values(? , ? , ? , ? , ?)
                """;

        dbConnect.executeUpdate(sql,
                grade.getStudentId(),
                grade.getSubjectId(),
                grade.getAttendance(),
                grade.getMidTerm(),
                grade.getFinalTerm()
        );

    }

    @Override
    public void updateGradeByStudentId(Grade grade) {
        final String sql = """
                update grades
                set attendance_score = ?,
                    midterm_score = ?,
                    final_score = ?
                where student_id = ? and subject_id = ?
                """;

        dbConnect.executeUpdate(sql,
                grade.getAttendance(),
                grade.getMidTerm(),
                grade.getFinalTerm(),
                grade.getStudentId(),
                grade.getSubjectId()
        );

    }

    @Override
    public void deleteGradeByStudentId(Grade grade) {
        final String sql = """
                delete from grades where student_id = ? and subject_id = ?
                """;

        dbConnect.executeUpdate(sql, grade.getStudentId(), grade.getSubjectId());


    }

    @Override
    public void addNewSubject(Grade grade, Integer credit) {
        final String sql = """
                insert into subjects(subject_name, credit) values(?, ?)
                """;

        dbConnect.executeUpdate(sql, grade.getSubjectName(), credit);
    }


}
