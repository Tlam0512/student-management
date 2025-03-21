package org.student.rmi.client.client;

import org.student.rmi.client.util.ConnectServer;
import org.student.rmi.server.domain.Account;
import org.student.rmi.server.service.IAccountService;

import java.rmi.RemoteException;

public class AccountClient {
    private final IAccountService accountService;

    public AccountClient() {
        try {
            accountService = (IAccountService) ConnectServer.getInstance()
                    .getRegistry()
                    .lookup("accountService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean login(Account account) throws RemoteException {
        return accountService.login(account);
    }

}
