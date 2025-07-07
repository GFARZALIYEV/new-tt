package stp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stp.model.JobVacancy;

public interface JobVacancyRepository extends JpaRepository<JobVacancy, Long> {
}
