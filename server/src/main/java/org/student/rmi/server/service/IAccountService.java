package org.student.rmi.server.service;

import org.student.rmi.server.domain.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAccountService extends Remote {
    boolean login(Account account) throws RemoteException;
    void addNewAccount(Account account) throws RemoteException;

    void deleteAccount(Account account) throws RemoteException;
}
