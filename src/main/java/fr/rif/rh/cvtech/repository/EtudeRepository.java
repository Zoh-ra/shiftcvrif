package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Etude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Etude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtudeRepository extends JpaRepository<Etude, Long> {}
