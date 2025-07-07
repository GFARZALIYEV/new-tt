package stp.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RequestVacancy {

    private Long userId;
    private Long vacancyId;

}
