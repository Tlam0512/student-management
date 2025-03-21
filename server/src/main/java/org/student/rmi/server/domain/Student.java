package org.student.rmi.server.domain;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private String address;

    public static Student mapToStudent(Object[] row) {
        return Student.builder()
                .id((String) row[0])
                .fullName((String) row[1])
                .dateOfBirth((Date) row[2])
                .gender((String) row[3])
                .email((String) row[4])
                .phone((String) row[5])
                .address((String) row[6])
                .build();
    }
}

