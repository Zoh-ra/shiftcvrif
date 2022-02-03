package fr.rif.rh.cvtech.web.rest;

import fr.rif.rh.cvtech.domain.Outil;
import fr.rif.rh.cvtech.repository.OutilRepository;
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
 * REST controller for managing {@link fr.rif.rh.cvtech.domain.Outil}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OutilResource {

    private final Logger log = LoggerFactory.getLogger(OutilResource.class);

    private static final String ENTITY_NAME = "outil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutilRepository outilRepository;

    public OutilResource(OutilRepository outilRepository) {
        this.outilRepository = outilRepository;
    }

    /**
     * {@code POST  /outils} : Create a new outil.
     *
     * @param outil the outil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outil, or with status {@code 400 (Bad Request)} if the outil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outils")
    public ResponseEntity<Outil> createOutil(@RequestBody Outil outil) throws URISyntaxException {
        log.debug("REST request to save Outil : {}", outil);
        if (outil.getId() != null) {
            throw new BadRequestAlertException("A new outil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Outil result = outilRepository.save(outil);
        return ResponseEntity
            .created(new URI("/api/outils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /outils/:id} : Updates an existing outil.
     *
     * @param id the id of the outil to save.
     * @param outil the outil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outil,
     * or with status {@code 400 (Bad Request)} if the outil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outils/{id}")
    public ResponseEntity<Outil> updateOutil(@PathVariable(value = "id", required = false) final Long id, @RequestBody Outil outil)
        throws URISyntaxException {
        log.debug("REST request to update Outil : {}, {}", id, outil);
        if (outil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, outil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!outilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Outil result = outilRepository.save(outil);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outil.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /outils/:id} : Partial updates given fields of an existing outil, field will ignore if it is null
     *
     * @param id the id of the outil to save.
     * @param outil the outil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outil,
     * or with status {@code 400 (Bad Request)} if the outil is not valid,
     * or with status {@code 404 (Not Found)} if the outil is not found,
     * or with status {@code 500 (Internal Server Error)} if the outil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/outils/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Outil> partialUpdateOutil(@PathVariable(value = "id", required = false) final Long id, @RequestBody Outil outil)
        throws URISyntaxException {
        log.debug("REST request to partial update Outil partially : {}, {}", id, outil);
        if (outil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, outil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!outilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Outil> result = outilRepository
            .findById(outil.getId())
            .map(existingOutil -> {
                if (outil.getNomOutil() != null) {
                    existingOutil.setNomOutil(outil.getNomOutil());
                }

                return existingOutil;
            })
            .map(outilRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outil.getId().toString())
        );
    }

    /**
     * {@code GET  /outils} : get all the outils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outils in body.
     */
    @GetMapping("/outils")
    public List<Outil> getAllOutils() {
        log.debug("REST request to get all Outils");
        return outilRepository.findAll();
    }

    /**
     * {@code GET  /outils/:id} : get the "id" outil.
     *
     * @param id the id of the outil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outils/{id}")
    public ResponseEntity<Outil> getOutil(@PathVariable Long id) {
        log.debug("REST request to get Outil : {}", id);
        Optional<Outil> outil = outilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(outil);
    }

    /**
     * {@code DELETE  /outils/:id} : delete the "id" outil.
     *
     * @param id the id of the outil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outils/{id}")
    public ResponseEntity<Void> deleteOutil(@PathVariable Long id) {
        log.debug("REST request to delete Outil : {}", id);
        outilRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
