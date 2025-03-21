package org.student.rmi.client.util;

import lombok.Getter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConnectServer {
    private static ConnectServer instance;
    @Getter
    private Registry registry;

    private ConnectServer() {
        try {
            System.out.println("✅ Connected to RMI Server!");
            registry = LocateRegistry.getRegistry("localhost", 1099);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectServer getInstance() {
        if (instance == null) {
            instance = new ConnectServer();
        }
        return instance;
    }


}
