package fr.rif.rh.cvtech.web.rest;

import fr.rif.rh.cvtech.domain.Programmation;
import fr.rif.rh.cvtech.repository.ProgrammationRepository;
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
 * REST controller for managing {@link fr.rif.rh.cvtech.domain.Programmation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProgrammationResource {

    private final Logger log = LoggerFactory.getLogger(ProgrammationResource.class);

    private static final String ENTITY_NAME = "programmation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgrammationRepository programmationRepository;

    public ProgrammationResource(ProgrammationRepository programmationRepository) {
        this.programmationRepository = programmationRepository;
    }

    /**
     * {@code POST  /programmations} : Create a new programmation.
     *
     * @param programmation the programmation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programmation, or with status {@code 400 (Bad Request)} if the programmation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/programmations")
    public ResponseEntity<Programmation> createProgrammation(@RequestBody Programmation programmation) throws URISyntaxException {
        log.debug("REST request to save Programmation : {}", programmation);
        if (programmation.getId() != null) {
            throw new BadRequestAlertException("A new programmation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Programmation result = programmationRepository.save(programmation);
        return ResponseEntity
            .created(new URI("/api/programmations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /programmations/:id} : Updates an existing programmation.
     *
     * @param id the id of the programmation to save.
     * @param programmation the programmation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programmation,
     * or with status {@code 400 (Bad Request)} if the programmation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programmation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/programmations/{id}")
    public ResponseEntity<Programmation> updateProgrammation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programmation programmation
    ) throws URISyntaxException {
        log.debug("REST request to update Programmation : {}, {}", id, programmation);
        if (programmation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programmation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Programmation result = programmationRepository.save(programmation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programmation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /programmations/:id} : Partial updates given fields of an existing programmation, field will ignore if it is null
     *
     * @param id the id of the programmation to save.
     * @param programmation the programmation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programmation,
     * or with status {@code 400 (Bad Request)} if the programmation is not valid,
     * or with status {@code 404 (Not Found)} if the programmation is not found,
     * or with status {@code 500 (Internal Server Error)} if the programmation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/programmations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Programmation> partialUpdateProgrammation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programmation programmation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Programmation partially : {}, {}", id, programmation);
        if (programmation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programmation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Programmation> result = programmationRepository
            .findById(programmation.getId())
            .map(existingProgrammation -> {
                if (programmation.getNomLangage() != null) {
                    existingProgrammation.setNomLangage(programmation.getNomLangage());
                }
                if (programmation.getTauxDeLangage() != null) {
                    existingProgrammation.setTauxDeLangage(programmation.getTauxDeLangage());
                }

                return existingProgrammation;
            })
            .map(programmationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programmation.getId().toString())
        );
    }

    /**
     * {@code GET  /programmations} : get all the programmations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programmations in body.
     */
    @GetMapping("/programmations")
    public List<Programmation> getAllProgrammations() {
        log.debug("REST request to get all Programmations");
        return programmationRepository.findAll();
    }

    /**
     * {@code GET  /programmations/:id} : get the "id" programmation.
     *
     * @param id the id of the programmation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programmation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programmations/{id}")
    public ResponseEntity<Programmation> getProgrammation(@PathVariable Long id) {
        log.debug("REST request to get Programmation : {}", id);
        Optional<Programmation> programmation = programmationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(programmation);
    }

    /**
     * {@code DELETE  /programmations/:id} : delete the "id" programmation.
     *
     * @param id the id of the programmation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/programmations/{id}")
    public ResponseEntity<Void> deleteProgrammation(@PathVariable Long id) {
        log.debug("REST request to delete Programmation : {}", id);
        programmationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
