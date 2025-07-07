package stp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stp.model.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query("select a from Authority a where a.authority=:authority")
    Optional<Authority> findByAuthority(String authority);
}
