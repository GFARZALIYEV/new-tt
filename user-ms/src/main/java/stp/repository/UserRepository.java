package stp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"authorities","userCv"})
    @Query("select u from User u left join u.authorities left join u.userCv where u.email=:email")
    Optional<User> findByEmail(String email);
}
