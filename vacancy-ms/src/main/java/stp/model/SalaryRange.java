package stp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SalaryRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double minSalary;
    private Double maxSalary;
    private boolean salaryNegotiable;

    @OneToMany(mappedBy = "salaryRange")
    @JsonIgnore
    @ToString.Exclude
    private List<JobVacancy> jobVacancies;
}
