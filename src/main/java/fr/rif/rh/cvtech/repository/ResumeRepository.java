package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Resume;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Resume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("select resume from Resume resume where resume.user.login = ?#{principal.username}")
    List<Resume> findByUserIsCurrentUser();
}
