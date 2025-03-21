package org.student.rmi.server.domain;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Grade implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String studentId;
    private Integer subjectId;
    private BigDecimal attendance;
    private BigDecimal midTerm;
    private BigDecimal finalTerm;
    private String studentName;
    private String subjectName;


    public static Grade mapToGrade(Object[] objects) {
        return Grade.builder()
                .id((Integer) objects[0])
                .studentId((String) objects[1])
                .subjectId((Integer) objects[2])
                .attendance((BigDecimal) objects[3])
                .midTerm((BigDecimal) objects[4])
                .finalTerm((BigDecimal) objects[5])
                .studentName((String) objects[6])
                .subjectName((String) objects[7])
                .build();
    }

}
