package fr.rif.rh.cvtech.web.rest;

import fr.rif.rh.cvtech.domain.Langue;
import fr.rif.rh.cvtech.repository.LangueRepository;
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
 * REST controller for managing {@link fr.rif.rh.cvtech.domain.Langue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LangueResource {

    private final Logger log = LoggerFactory.getLogger(LangueResource.class);

    private static final String ENTITY_NAME = "langue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LangueRepository langueRepository;

    public LangueResource(LangueRepository langueRepository) {
        this.langueRepository = langueRepository;
    }

    /**
     * {@code POST  /langues} : Create a new langue.
     *
     * @param langue the langue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new langue, or with status {@code 400 (Bad Request)} if the langue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/langues")
    public ResponseEntity<Langue> createLangue(@RequestBody Langue langue) throws URISyntaxException {
        log.debug("REST request to save Langue : {}", langue);
        if (langue.getId() != null) {
            throw new BadRequestAlertException("A new langue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Langue result = langueRepository.save(langue);
        return ResponseEntity
            .created(new URI("/api/langues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /langues/:id} : Updates an existing langue.
     *
     * @param id the id of the langue to save.
     * @param langue the langue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated langue,
     * or with status {@code 400 (Bad Request)} if the langue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the langue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/langues/{id}")
    public ResponseEntity<Langue> updateLangue(@PathVariable(value = "id", required = false) final Long id, @RequestBody Langue langue)
        throws URISyntaxException {
        log.debug("REST request to update Langue : {}, {}", id, langue);
        if (langue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, langue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!langueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Langue result = langueRepository.save(langue);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, langue.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /langues/:id} : Partial updates given fields of an existing langue, field will ignore if it is null
     *
     * @param id the id of the langue to save.
     * @param langue the langue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated langue,
     * or with status {@code 400 (Bad Request)} if the langue is not valid,
     * or with status {@code 404 (Not Found)} if the langue is not found,
     * or with status {@code 500 (Internal Server Error)} if the langue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/langues/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Langue> partialUpdateLangue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Langue langue
    ) throws URISyntaxException {
        log.debug("REST request to partial update Langue partially : {}, {}", id, langue);
        if (langue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, langue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!langueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Langue> result = langueRepository
            .findById(langue.getId())
            .map(existingLangue -> {
                if (langue.getLangue() != null) {
                    existingLangue.setLangue(langue.getLangue());
                }

                return existingLangue;
            })
            .map(langueRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, langue.getId().toString())
        );
    }

    /**
     * {@code GET  /langues} : get all the langues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of langues in body.
     */
    @GetMapping("/langues")
    public List<Langue> getAllLangues() {
        log.debug("REST request to get all Langues");
        return langueRepository.findAll();
    }

    /**
     * {@code GET  /langues/:id} : get the "id" langue.
     *
     * @param id the id of the langue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the langue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/langues/{id}")
    public ResponseEntity<Langue> getLangue(@PathVariable Long id) {
        log.debug("REST request to get Langue : {}", id);
        Optional<Langue> langue = langueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(langue);
    }

    /**
     * {@code DELETE  /langues/:id} : delete the "id" langue.
     *
     * @param id the id of the langue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/langues/{id}")
    public ResponseEntity<Void> deleteLangue(@PathVariable Long id) {
        log.debug("REST request to delete Langue : {}", id);
        langueRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
