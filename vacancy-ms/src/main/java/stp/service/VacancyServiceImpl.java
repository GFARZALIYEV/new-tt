package stp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stp.dto.RequestVacancy;
import stp.model.JobVacancy;
import stp.model.VacancyApplication;
import stp.repository.JobVacancyRepository;
import stp.repository.VacancyApplicationRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class VacancyServiceImpl implements VacancyService {
    private final JobVacancyRepository jobVacancyRepository;
    private final VacancyApplicationRepository vacancyApplicationRepository;

    @Override
    public String apply(RequestVacancy requestVacancy) {
        JobVacancy vacancy = jobVacancyRepository.
                findById(requestVacancy.getVacancyId())
                .orElseThrow(() -> new RuntimeException("Vacancy not found"));
        Set<Long> userIds = vacancy.getUserIds();
        if (userIds == null || userIds.size() == 0) {
            userIds = new HashSet<>();
        }
        userIds.add(requestVacancy.getUserId());
        vacancy.setUserIds(userIds);
        VacancyApplication vacancyApplication = VacancyApplication.builder()
                .vacancyId(requestVacancy.getVacancyId())
                .appliedAt(LocalDateTime.now())
                .userId(requestVacancy.getUserId())
                .build();
        vacancyApplicationRepository.save(vacancyApplication);
        return "Application submitted successfully.";
    }
}
