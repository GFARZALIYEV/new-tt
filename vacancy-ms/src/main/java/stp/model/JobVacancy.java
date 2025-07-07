package stp.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stp.enums.WorkType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(attributeNodes = {
        @NamedAttributeNode("company"),
        @NamedAttributeNode("experienceLevels"),
        @NamedAttributeNode("locations"),
        @NamedAttributeNode("educationLevels"),
        @NamedAttributeNode("salaryRange")
})
public class JobVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String jobDescription;

    @ManyToOne
    private SalaryRange salaryRange;

    @ManyToOne
    @JsonManagedReference
    private Company company;

    @Column(length = 5000)
    private String requirements;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "jobs_experience_level",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "experience_level_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "experience_level_id"})
    )
    private Set<ExperienceLevel> experienceLevels;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "jobs_locations",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "location_id"})
    )
    private Set<Location> locations;


    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "jobs_education_level",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "education_level_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "education_level_id"})
    )
    private Set<EducationLevel> educationLevels;

    @ElementCollection
    private Set<Long> userIds;

    @Enumerated(EnumType.STRING)
    private WorkType workType;

    private LocalDate postedDate;
    private LocalDate applicationDeadline;

    @ElementCollection
    private List<String> benefits;


}
