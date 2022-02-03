package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Experience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Experience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {}
