package stp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stp.model.VacancyApplication;

public interface VacancyApplicationRepository extends JpaRepository<VacancyApplication, Long> {
}
