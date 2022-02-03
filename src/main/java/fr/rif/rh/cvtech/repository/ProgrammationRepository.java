package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Programmation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Programmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammationRepository extends JpaRepository<Programmation, Long> {}
