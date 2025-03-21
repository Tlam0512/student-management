package org.student.rmi.server.service.impl;

import org.student.rmi.server.domain.Account;
import org.student.rmi.server.service.IAccountService;
import org.student.rmi.server.util.DbConnect;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountServiceImpl extends UnicastRemoteObject
        implements IAccountService {

    private final DbConnect dbConnect = DbConnect.getInstance();

    public AccountServiceImpl() throws RemoteException {
    }

    @Override
    public boolean login(Account account) throws RemoteException {
        final String sql = """
                select * from users
                where username = ? and password_hash = ?
                """;

        return !dbConnect.executeQuery(sql, account.getUsername(), account.getPassword()).isEmpty();
    }


    @Override
    public void addNewAccount(Account account) throws RemoteException {
        if (isUsernameExisted(account.getUsername())) {
            throw new RemoteException("Username is already existed");
        }

        final String sql = """
                insert into users(username, password_hash)
                values (?, ?)
                """;

        dbConnect.executeUpdate(sql, account.getUsername(), account.getPassword());
    }

    @Override
    public void deleteAccount(Account account) throws RemoteException {
        final String sql = """
                delete from users
                where username = ?
                """;

        dbConnect.executeUpdate(sql, account.getUsername());
    }


    private boolean isUsernameExisted(String username) {
        final String sql = """
                select * from users
                where username = ?
                """;

        return !dbConnect.executeQuery(sql, username).isEmpty();
    }



}
