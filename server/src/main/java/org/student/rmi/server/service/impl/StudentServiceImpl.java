package org.student.rmi.server.service.impl;

import org.student.rmi.server.domain.Student;
import org.student.rmi.server.service.IStudentService;
import org.student.rmi.server.util.DbConnect;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class StudentServiceImpl extends UnicastRemoteObject
        implements IStudentService {

    private final DbConnect dbConnect = DbConnect.getInstance();

    public StudentServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Student> getAllStudents() throws Exception {
        final String sql = """
                select * from students
                """;

        return dbConnect.executeQuery(sql).stream()
                .map(Student::mapToStudent)
                .toList();
    }

    @Override
    public void addNewStudent(Student student) throws Exception {
        if (isStudentExist(student.getId())) {
            throw new Exception("Mã sinh viên đã tồn tại!");
        }

        if (isEmailExist(student.getEmail())) {
            throw new Exception("Email đã được sử dụng");
        }

        if (isPhoneExist(student.getPhone())) {
            throw new Exception("Số điện thoại đã được sử dụng");
        }

        final String sql = """
                insert into students(student_id, full_name, date_of_birth, gender, email, phone, address)
                values (?, ?, ?, ?, ?, ?, ?)
                """;

        dbConnect.executeUpdate(sql,
                student.getId(),
                student.getFullName(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getEmail(),
                student.getPhone(),
                student.getAddress()
        );
    }

    @Override
    public void deleteStudent(String studentId) throws Exception {
        final String sql = """
                delete from students where student_id = ?
                """;

        dbConnect.executeUpdate(sql, studentId);
    }

    @Override
    public void updateStudent(Student student) throws Exception {
        if (!isStudentExist(student.getId())) {
            throw new Exception("Sinh viên không tồn tại trong hệ thống");
        }

        if (isEmailExist(student.getEmail(), student.getId())) {
            throw new Exception("Email đã tồn tại trong hệ thống");
        }

        if (isPhoneExist(student.getPhone(), student.getId())) {
            throw new Exception("Số điện thoại đã tồn tại trong hệ thống");
        }

        final String sql = """
                update students set 
                full_name = ?,
                date_of_birth = ?,
                gender = ?,
                email = ?,
                phone = ?,
                address = ?
                where student_id = ?
                """;

        dbConnect.executeUpdate(sql,
                student.getFullName(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getEmail(),
                student.getPhone(),
                student.getAddress(),
                student.getId()
        );
    }

    @Override
    public List<Student> findStudent(String keyword) throws Exception {
        System.out.println("Keyword: " + keyword);


        List<Student> students = getAllStudents();

        if (keyword.isEmpty()) {
            return students;
        }


        return students.stream()
                .filter(student -> student.getFullName().contains(keyword)
                        || student.getId().toLowerCase().contains(keyword)
                        || student.getEmail().toLowerCase().contains(keyword)
                        || student.getPhone().toLowerCase().contains(keyword)
                        || student.getAddress().toLowerCase().contains(keyword)
                        || student.getGender().toLowerCase().contains(keyword)
                )
                .toList();
    }

    private boolean isStudentExist(String id) {
        final String sql = """
                select * from students where student_id = ?
                """;

        return !dbConnect.executeQuery(sql, id).isEmpty();
    }

    private boolean isEmailExist(String email) {
        final String sql = """
                select * from students where email = ?
                """;

        return !dbConnect.executeQuery(sql, email).isEmpty();
    }

    private boolean isEmailExist(String email, String studentId) {
        final String sql = """
                select * from students where email = ? and student_id != ?
                """;

        return !dbConnect.executeQuery(sql, email, studentId).isEmpty();
    }

    private boolean isPhoneExist(String phone) {
        final String sql = """
                select * from students where phone = ?
                """;

        return !dbConnect.executeQuery(sql, phone).isEmpty();
    }

    private boolean isPhoneExist(String phone, String studentId) {
        final String sql = """
                select * from students where phone = ? and student_id != ?
                """;

        return !dbConnect.executeQuery(sql, phone, studentId).isEmpty();
    }


}
