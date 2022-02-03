package fr.rif.rh.cvtech.repository;

import fr.rif.rh.cvtech.domain.Design;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Design entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {}
