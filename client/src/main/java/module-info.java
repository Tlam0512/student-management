module client {
    requires javafx.fxml;
    requires server;
    requires java.rmi;
    requires static lombok;
    requires javafx.controls;
    requires java.sql;

    opens org.student.rmi.client to javafx.fxml;
    opens org.student.rmi.client.controller to javafx.fxml;

    exports org.student.rmi.client;
    exports org.student.rmi.client.controller;

}