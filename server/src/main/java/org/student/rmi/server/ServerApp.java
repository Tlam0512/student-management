package org.student.rmi.server;

import org.student.rmi.server.service.IAccountService;
import org.student.rmi.server.service.IGradeService;
import org.student.rmi.server.service.IStudentService;
import org.student.rmi.server.service.impl.AccountServiceImpl;
import org.student.rmi.server.service.impl.GradeServiceImpl;
import org.student.rmi.server.service.impl.StudentServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerApp {
    public static void main(String[] args) {

        try {
            IAccountService accountService = new AccountServiceImpl();
            IStudentService studentService = new StudentServiceImpl();
            IGradeService gradeService = new GradeServiceImpl();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("accountService", accountService);
            registry.rebind("studentService", studentService);
            registry.rebind("gradeService", gradeService);

            System.out.println("🚀 Server is running...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}