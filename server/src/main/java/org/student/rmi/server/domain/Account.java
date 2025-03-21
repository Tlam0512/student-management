package org.student.rmi.server.domain;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private String username;
    private String password;


    public static Account mapToAccount(Object[] row) {
        return Account.builder()
                .username((String) row[1])
                .password((String) row[2])
                .build();
    }

}
