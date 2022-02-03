package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Profil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {}
