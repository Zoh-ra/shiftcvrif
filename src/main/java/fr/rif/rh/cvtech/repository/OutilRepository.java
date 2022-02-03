package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Outil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Outil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutilRepository extends JpaRepository<Outil, Long> {}
