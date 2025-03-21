module server {
    requires java.sql;
    requires mysql.connector.j;
    requires java.rmi;
    requires static lombok;

    exports org.student.rmi.server.service;
    exports org.student.rmi.server.service.impl;
    exports org.student.rmi.server.domain;
    exports org.student.rmi.server;

    opens org.student.rmi.server.service;
    opens org.student.rmi.server.service.impl;
    opens org.student.rmi.server.domain;
    opens org.student.rmi.server;
}