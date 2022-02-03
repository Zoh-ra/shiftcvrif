package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Langue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Langue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LangueRepository extends JpaRepository<Langue, Long> {}
