package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Etude;
import fr.rif.rh.cvtech.repository.EtudeRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EtudeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtudeResourceIT {

    private static final String DEFAULT_NOM_ETUDE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ETUDE = "BBBBBBBBBB";

    private static final Instant DEFAULT_ANNEE_ETUDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANNEE_ETUDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/etudes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtudeRepository etudeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtudeMockMvc;

    private Etude etude;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etude createEntity(EntityManager em) {
        Etude etude = new Etude().nomEtude(DEFAULT_NOM_ETUDE).anneeEtude(DEFAULT_ANNEE_ETUDE);
        return etude;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etude createUpdatedEntity(EntityManager em) {
        Etude etude = new Etude().nomEtude(UPDATED_NOM_ETUDE).anneeEtude(UPDATED_ANNEE_ETUDE);
        return etude;
    }

    @BeforeEach
    public void initTest() {
        etude = createEntity(em);
    }

    @Test
    @Transactional
    void createEtude() throws Exception {
        int databaseSizeBeforeCreate = etudeRepository.findAll().size();
        // Create the Etude
        restEtudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etude)))
            .andExpect(status().isCreated());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeCreate + 1);
        Etude testEtude = etudeList.get(etudeList.size() - 1);
        assertThat(testEtude.getNomEtude()).isEqualTo(DEFAULT_NOM_ETUDE);
        assertThat(testEtude.getAnneeEtude()).isEqualTo(DEFAULT_ANNEE_ETUDE);
    }

    @Test
    @Transactional
    void createEtudeWithExistingId() throws Exception {
        // Create the Etude with an existing ID
        etude.setId(1L);

        int databaseSizeBeforeCreate = etudeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etude)))
            .andExpect(status().isBadRequest());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtudes() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        // Get all the etudeList
        restEtudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomEtude").value(hasItem(DEFAULT_NOM_ETUDE)))
            .andExpect(jsonPath("$.[*].anneeEtude").value(hasItem(DEFAULT_ANNEE_ETUDE.toString())));
    }

    @Test
    @Transactional
    void getEtude() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        // Get the etude
        restEtudeMockMvc
            .perform(get(ENTITY_API_URL_ID, etude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etude.getId().intValue()))
            .andExpect(jsonPath("$.nomEtude").value(DEFAULT_NOM_ETUDE))
            .andExpect(jsonPath("$.anneeEtude").value(DEFAULT_ANNEE_ETUDE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEtude() throws Exception {
        // Get the etude
        restEtudeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtude() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();

        // Update the etude
        Etude updatedEtude = etudeRepository.findById(etude.getId()).get();
        // Disconnect from session so that the updates on updatedEtude are not directly saved in db
        em.detach(updatedEtude);
        updatedEtude.nomEtude(UPDATED_NOM_ETUDE).anneeEtude(UPDATED_ANNEE_ETUDE);

        restEtudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtude))
            )
            .andExpect(status().isOk());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
        Etude testEtude = etudeList.get(etudeList.size() - 1);
        assertThat(testEtude.getNomEtude()).isEqualTo(UPDATED_NOM_ETUDE);
        assertThat(testEtude.getAnneeEtude()).isEqualTo(UPDATED_ANNEE_ETUDE);
    }

    @Test
    @Transactional
    void putNonExistingEtude() throws Exception {
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();
        etude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etude))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtude() throws Exception {
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();
        etude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etude))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtude() throws Exception {
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();
        etude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etude)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtudeWithPatch() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();

        // Update the etude using partial update
        Etude partialUpdatedEtude = new Etude();
        partialUpdatedEtude.setId(etude.getId());

        partialUpdatedEtude.anneeEtude(UPDATED_ANNEE_ETUDE);

        restEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtude))
            )
            .andExpect(status().isOk());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
        Etude testEtude = etudeList.get(etudeList.size() - 1);
        assertThat(testEtude.getNomEtude()).isEqualTo(DEFAULT_NOM_ETUDE);
        assertThat(testEtude.getAnneeEtude()).isEqualTo(UPDATED_ANNEE_ETUDE);
    }

    @Test
    @Transactional
    void fullUpdateEtudeWithPatch() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();

        // Update the etude using partial update
        Etude partialUpdatedEtude = new Etude();
        partialUpdatedEtude.setId(etude.getId());

        partialUpdatedEtude.nomEtude(UPDATED_NOM_ETUDE).anneeEtude(UPDATED_ANNEE_ETUDE);

        restEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtude))
            )
            .andExpect(status().isOk());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
        Etude testEtude = etudeList.get(etudeList.size() - 1);
        assertThat(testEtude.getNomEtude()).isEqualTo(UPDATED_NOM_ETUDE);
        assertThat(testEtude.getAnneeEtude()).isEqualTo(UPDATED_ANNEE_ETUDE);
    }

    @Test
    @Transactional
    void patchNonExistingEtude() throws Exception {
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();
        etude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etude))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtude() throws Exception {
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();
        etude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etude))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtude() throws Exception {
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();
        etude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etude)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtude() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        int databaseSizeBeforeDelete = etudeRepository.findAll().size();

        // Delete the etude
        restEtudeMockMvc
            .perform(delete(ENTITY_API_URL_ID, etude.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
