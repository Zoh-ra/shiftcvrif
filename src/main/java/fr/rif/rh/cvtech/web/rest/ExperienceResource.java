package fr.rif.rh.cvtech.web.rest;

import fr.rif.rh.cvtech.domain.Experience;
import fr.rif.rh.cvtech.repository.ExperienceRepository;
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
 * REST controller for managing {@link fr.rif.rh.cvtech.domain.Experience}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceResource.class);

    private static final String ENTITY_NAME = "experience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExperienceRepository experienceRepository;

    public ExperienceResource(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    /**
     * {@code POST  /experiences} : Create a new experience.
     *
     * @param experience the experience to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new experience, or with status {@code 400 (Bad Request)} if the experience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/experiences")
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) throws URISyntaxException {
        log.debug("REST request to save Experience : {}", experience);
        if (experience.getId() != null) {
            throw new BadRequestAlertException("A new experience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Experience result = experienceRepository.save(experience);
        return ResponseEntity
            .created(new URI("/api/experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /experiences/:id} : Updates an existing experience.
     *
     * @param id the id of the experience to save.
     * @param experience the experience to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experience,
     * or with status {@code 400 (Bad Request)} if the experience is not valid,
     * or with status {@code 500 (Internal Server Error)} if the experience couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/experiences/{id}")
    public ResponseEntity<Experience> updateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Experience experience
    ) throws URISyntaxException {
        log.debug("REST request to update Experience : {}, {}", id, experience);
        if (experience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experience.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Experience result = experienceRepository.save(experience);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, experience.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /experiences/:id} : Partial updates given fields of an existing experience, field will ignore if it is null
     *
     * @param id the id of the experience to save.
     * @param experience the experience to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experience,
     * or with status {@code 400 (Bad Request)} if the experience is not valid,
     * or with status {@code 404 (Not Found)} if the experience is not found,
     * or with status {@code 500 (Internal Server Error)} if the experience couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/experiences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Experience> partialUpdateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Experience experience
    ) throws URISyntaxException {
        log.debug("REST request to partial update Experience partially : {}, {}", id, experience);
        if (experience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experience.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Experience> result = experienceRepository
            .findById(experience.getId())
            .map(existingExperience -> {
                if (experience.getNomEntreprise() != null) {
                    existingExperience.setNomEntreprise(experience.getNomEntreprise());
                }
                if (experience.getNomPoste() != null) {
                    existingExperience.setNomPoste(experience.getNomPoste());
                }
                if (experience.getDateExperience() != null) {
                    existingExperience.setDateExperience(experience.getDateExperience());
                }
                if (experience.getDescriptionExperience() != null) {
                    existingExperience.setDescriptionExperience(experience.getDescriptionExperience());
                }

                return existingExperience;
            })
            .map(experienceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, experience.getId().toString())
        );
    }

    /**
     * {@code GET  /experiences} : get all the experiences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of experiences in body.
     */
    @GetMapping("/experiences")
    public List<Experience> getAllExperiences() {
        log.debug("REST request to get all Experiences");
        return experienceRepository.findAll();
    }

    /**
     * {@code GET  /experiences/:id} : get the "id" experience.
     *
     * @param id the id of the experience to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the experience, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/experiences/{id}")
    public ResponseEntity<Experience> getExperience(@PathVariable Long id) {
        log.debug("REST request to get Experience : {}", id);
        Optional<Experience> experience = experienceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(experience);
    }

    /**
     * {@code DELETE  /experiences/:id} : delete the "id" experience.
     *
     * @param id the id of the experience to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        log.debug("REST request to delete Experience : {}", id);
        experienceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
