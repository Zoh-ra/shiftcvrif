package fr.rif.rh.cvtech.web.rest;

import fr.rif.rh.cvtech.domain.Adresse;
import fr.rif.rh.cvtech.repository.AdresseRepository;
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
 * REST controller for managing {@link fr.rif.rh.cvtech.domain.Adresse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdresseResource {

    private final Logger log = LoggerFactory.getLogger(AdresseResource.class);

    private static final String ENTITY_NAME = "adresse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresseRepository adresseRepository;

    public AdresseResource(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }

    /**
     * {@code POST  /adresses} : Create a new adresse.
     *
     * @param adresse the adresse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresse, or with status {@code 400 (Bad Request)} if the adresse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adresses")
    public ResponseEntity<Adresse> createAdresse(@RequestBody Adresse adresse) throws URISyntaxException {
        log.debug("REST request to save Adresse : {}", adresse);
        if (adresse.getId() != null) {
            throw new BadRequestAlertException("A new adresse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Adresse result = adresseRepository.save(adresse);
        return ResponseEntity
            .created(new URI("/api/adresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adresses/:id} : Updates an existing adresse.
     *
     * @param id the id of the adresse to save.
     * @param adresse the adresse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresse,
     * or with status {@code 400 (Bad Request)} if the adresse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adresses/{id}")
    public ResponseEntity<Adresse> updateAdresse(@PathVariable(value = "id", required = false) final Long id, @RequestBody Adresse adresse)
        throws URISyntaxException {
        log.debug("REST request to update Adresse : {}, {}", id, adresse);
        if (adresse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Adresse result = adresseRepository.save(adresse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adresse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adresses/:id} : Partial updates given fields of an existing adresse, field will ignore if it is null
     *
     * @param id the id of the adresse to save.
     * @param adresse the adresse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresse,
     * or with status {@code 400 (Bad Request)} if the adresse is not valid,
     * or with status {@code 404 (Not Found)} if the adresse is not found,
     * or with status {@code 500 (Internal Server Error)} if the adresse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Adresse> partialUpdateAdresse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Adresse adresse
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adresse partially : {}, {}", id, adresse);
        if (adresse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Adresse> result = adresseRepository
            .findById(adresse.getId())
            .map(existingAdresse -> {
                if (adresse.getAdresse() != null) {
                    existingAdresse.setAdresse(adresse.getAdresse());
                }
                if (adresse.getNomVille() != null) {
                    existingAdresse.setNomVille(adresse.getNomVille());
                }
                if (adresse.getCodePostale() != null) {
                    existingAdresse.setCodePostale(adresse.getCodePostale());
                }

                return existingAdresse;
            })
            .map(adresseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adresse.getId().toString())
        );
    }

    /**
     * {@code GET  /adresses} : get all the adresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresses in body.
     */
    @GetMapping("/adresses")
    public List<Adresse> getAllAdresses() {
        log.debug("REST request to get all Adresses");
        return adresseRepository.findAll();
    }

    /**
     * {@code GET  /adresses/:id} : get the "id" adresse.
     *
     * @param id the id of the adresse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adresses/{id}")
    public ResponseEntity<Adresse> getAdresse(@PathVariable Long id) {
        log.debug("REST request to get Adresse : {}", id);
        Optional<Adresse> adresse = adresseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adresse);
    }

    /**
     * {@code DELETE  /adresses/:id} : delete the "id" adresse.
     *
     * @param id the id of the adresse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adresses/{id}")
    public ResponseEntity<Void> deleteAdresse(@PathVariable Long id) {
        log.debug("REST request to delete Adresse : {}", id);
        adresseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
