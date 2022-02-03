package fr.rif.rh.cvtech.web.rest;

import fr.rif.rh.cvtech.domain.Design;
import fr.rif.rh.cvtech.repository.DesignRepository;
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
 * REST controller for managing {@link fr.rif.rh.cvtech.domain.Design}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DesignResource {

    private final Logger log = LoggerFactory.getLogger(DesignResource.class);

    private static final String ENTITY_NAME = "design";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesignRepository designRepository;

    public DesignResource(DesignRepository designRepository) {
        this.designRepository = designRepository;
    }

    /**
     * {@code POST  /designs} : Create a new design.
     *
     * @param design the design to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new design, or with status {@code 400 (Bad Request)} if the design has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/designs")
    public ResponseEntity<Design> createDesign(@RequestBody Design design) throws URISyntaxException {
        log.debug("REST request to save Design : {}", design);
        if (design.getId() != null) {
            throw new BadRequestAlertException("A new design cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Design result = designRepository.save(design);
        return ResponseEntity
            .created(new URI("/api/designs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /designs/:id} : Updates an existing design.
     *
     * @param id the id of the design to save.
     * @param design the design to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated design,
     * or with status {@code 400 (Bad Request)} if the design is not valid,
     * or with status {@code 500 (Internal Server Error)} if the design couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/designs/{id}")
    public ResponseEntity<Design> updateDesign(@PathVariable(value = "id", required = false) final Long id, @RequestBody Design design)
        throws URISyntaxException {
        log.debug("REST request to update Design : {}, {}", id, design);
        if (design.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, design.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Design result = designRepository.save(design);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, design.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /designs/:id} : Partial updates given fields of an existing design, field will ignore if it is null
     *
     * @param id the id of the design to save.
     * @param design the design to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated design,
     * or with status {@code 400 (Bad Request)} if the design is not valid,
     * or with status {@code 404 (Not Found)} if the design is not found,
     * or with status {@code 500 (Internal Server Error)} if the design couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/designs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Design> partialUpdateDesign(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Design design
    ) throws URISyntaxException {
        log.debug("REST request to partial update Design partially : {}, {}", id, design);
        if (design.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, design.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Design> result = designRepository
            .findById(design.getId())
            .map(existingDesign -> {
                if (design.getNomDesign() != null) {
                    existingDesign.setNomDesign(design.getNomDesign());
                }
                if (design.getTauxDeDesign() != null) {
                    existingDesign.setTauxDeDesign(design.getTauxDeDesign());
                }

                return existingDesign;
            })
            .map(designRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, design.getId().toString())
        );
    }

    /**
     * {@code GET  /designs} : get all the designs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of designs in body.
     */
    @GetMapping("/designs")
    public List<Design> getAllDesigns() {
        log.debug("REST request to get all Designs");
        return designRepository.findAll();
    }

    /**
     * {@code GET  /designs/:id} : get the "id" design.
     *
     * @param id the id of the design to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the design, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/designs/{id}")
    public ResponseEntity<Design> getDesign(@PathVariable Long id) {
        log.debug("REST request to get Design : {}", id);
        Optional<Design> design = designRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(design);
    }

    /**
     * {@code DELETE  /designs/:id} : delete the "id" design.
     *
     * @param id the id of the design to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/designs/{id}")
    public ResponseEntity<Void> deleteDesign(@PathVariable Long id) {
        log.debug("REST request to delete Design : {}", id);
        designRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
