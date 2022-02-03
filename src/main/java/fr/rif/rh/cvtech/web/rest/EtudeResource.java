package fr.rif.rh.cvtech.web.rest;

import fr.rif.rh.cvtech.domain.Etude;
import fr.rif.rh.cvtech.repository.EtudeRepository;
import fr.rif.rh.cvtech.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.rif.rh.cvtech.domain.Etude}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EtudeResource {

    private final Logger log = LoggerFactory.getLogger(EtudeResource.class);

    private static final String ENTITY_NAME = "etude";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtudeRepository etudeRepository;

    public EtudeResource(EtudeRepository etudeRepository) {
        this.etudeRepository = etudeRepository;
    }

    /**
     * {@code POST  /etudes} : Create a new etude.
     *
     * @param etude the etude to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etude, or with status {@code 400 (Bad Request)} if the etude has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etudes")
    public ResponseEntity<Etude> createEtude(@RequestBody Etude etude) throws URISyntaxException {
        log.debug("REST request to save Etude : {}", etude);
        if (etude.getId() != null) {
            throw new BadRequestAlertException("A new etude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etude result = etudeRepository.save(etude);
        return ResponseEntity
            .created(new URI("/api/etudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etudes/:id} : Updates an existing etude.
     *
     * @param id the id of the etude to save.
     * @param etude the etude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etude,
     * or with status {@code 400 (Bad Request)} if the etude is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etudes/{id}")
    public ResponseEntity<Etude> updateEtude(@PathVariable(value = "id", required = false) final Long id, @RequestBody Etude etude)
        throws URISyntaxException {
        log.debug("REST request to update Etude : {}, {}", id, etude);
        if (etude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etudeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Etude result = etudeRepository.save(etude);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etude.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etudes/:id} : Partial updates given fields of an existing etude, field will ignore if it is null
     *
     * @param id the id of the etude to save.
     * @param etude the etude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etude,
     * or with status {@code 400 (Bad Request)} if the etude is not valid,
     * or with status {@code 404 (Not Found)} if the etude is not found,
     * or with status {@code 500 (Internal Server Error)} if the etude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etudes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Etude> partialUpdateEtude(@PathVariable(value = "id", required = false) final Long id, @RequestBody Etude etude)
        throws URISyntaxException {
        log.debug("REST request to partial update Etude partially : {}, {}", id, etude);
        if (etude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etudeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Etude> result = etudeRepository
            .findById(etude.getId())
            .map(existingEtude -> {
                if (etude.getNomEtude() != null) {
                    existingEtude.setNomEtude(etude.getNomEtude());
                }
                if (etude.getAnneeEtude() != null) {
                    existingEtude.setAnneeEtude(etude.getAnneeEtude());
                }

                return existingEtude;
            })
            .map(etudeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etude.getId().toString())
        );
    }

    /**
     * {@code GET  /etudes} : get all the etudes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etudes in body.
     */
    @GetMapping("/etudes")
    public List<Etude> getAllEtudes() {
        log.debug("REST request to get all Etudes");
        return etudeRepository.findAll();
    }

    /**
     * {@code GET  /etudes/:id} : get the "id" etude.
     *
     * @param id the id of the etude to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etude, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etudes/{id}")
    public ResponseEntity<Etude> getEtude(@PathVariable Long id) {
        log.debug("REST request to get Etude : {}", id);
        Optional<Etude> etude = etudeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(etude);
    }

    /**
     * {@code DELETE  /etudes/:id} : delete the "id" etude.
     *
     * @param id the id of the etude to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etudes/{id}")
    public ResponseEntity<Void> deleteEtude(@PathVariable Long id) {
        log.debug("REST request to delete Etude : {}", id);
        etudeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
