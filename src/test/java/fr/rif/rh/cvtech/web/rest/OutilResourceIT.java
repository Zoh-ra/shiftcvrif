package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Outil;
import fr.rif.rh.cvtech.repository.OutilRepository;
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
 * Integration tests for the {@link OutilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OutilResourceIT {

    private static final String DEFAULT_NOM_OUTIL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_OUTIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/outils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OutilRepository outilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOutilMockMvc;

    private Outil outil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outil createEntity(EntityManager em) {
        Outil outil = new Outil().nomOutil(DEFAULT_NOM_OUTIL);
        return outil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outil createUpdatedEntity(EntityManager em) {
        Outil outil = new Outil().nomOutil(UPDATED_NOM_OUTIL);
        return outil;
    }

    @BeforeEach
    public void initTest() {
        outil = createEntity(em);
    }

    @Test
    @Transactional
    void createOutil() throws Exception {
        int databaseSizeBeforeCreate = outilRepository.findAll().size();
        // Create the Outil
        restOutilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isCreated());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeCreate + 1);
        Outil testOutil = outilList.get(outilList.size() - 1);
        assertThat(testOutil.getNomOutil()).isEqualTo(DEFAULT_NOM_OUTIL);
    }

    @Test
    @Transactional
    void createOutilWithExistingId() throws Exception {
        // Create the Outil with an existing ID
        outil.setId(1L);

        int databaseSizeBeforeCreate = outilRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isBadRequest());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOutils() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        // Get all the outilList
        restOutilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomOutil").value(hasItem(DEFAULT_NOM_OUTIL)));
    }

    @Test
    @Transactional
    void getOutil() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        // Get the outil
        restOutilMockMvc
            .perform(get(ENTITY_API_URL_ID, outil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outil.getId().intValue()))
            .andExpect(jsonPath("$.nomOutil").value(DEFAULT_NOM_OUTIL));
    }

    @Test
    @Transactional
    void getNonExistingOutil() throws Exception {
        // Get the outil
        restOutilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOutil() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        int databaseSizeBeforeUpdate = outilRepository.findAll().size();

        // Update the outil
        Outil updatedOutil = outilRepository.findById(outil.getId()).get();
        // Disconnect from session so that the updates on updatedOutil are not directly saved in db
        em.detach(updatedOutil);
        updatedOutil.nomOutil(UPDATED_NOM_OUTIL);

        restOutilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOutil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOutil))
            )
            .andExpect(status().isOk());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
        Outil testOutil = outilList.get(outilList.size() - 1);
        assertThat(testOutil.getNomOutil()).isEqualTo(UPDATED_NOM_OUTIL);
    }

    @Test
    @Transactional
    void putNonExistingOutil() throws Exception {
        int databaseSizeBeforeUpdate = outilRepository.findAll().size();
        outil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, outil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(outil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOutil() throws Exception {
        int databaseSizeBeforeUpdate = outilRepository.findAll().size();
        outil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOutilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(outil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOutil() throws Exception {
        int databaseSizeBeforeUpdate = outilRepository.findAll().size();
        outil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOutilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOutilWithPatch() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        int databaseSizeBeforeUpdate = outilRepository.findAll().size();

        // Update the outil using partial update
        Outil partialUpdatedOutil = new Outil();
        partialUpdatedOutil.setId(outil.getId());

        restOutilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOutil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOutil))
            )
            .andExpect(status().isOk());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
        Outil testOutil = outilList.get(outilList.size() - 1);
        assertThat(testOutil.getNomOutil()).isEqualTo(DEFAULT_NOM_OUTIL);
    }

    @Test
    @Transactional
    void fullUpdateOutilWithPatch() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        int databaseSizeBeforeUpdate = outilRepository.findAll().size();

        // Update the outil using partial update
        Outil partialUpdatedOutil = new Outil();
        partialUpdatedOutil.setId(outil.getId());

        partialUpdatedOutil.nomOutil(UPDATED_NOM_OUTIL);

        restOutilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOutil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOutil))
            )
            .andExpect(status().isOk());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
        Outil testOutil = outilList.get(outilList.size() - 1);
        assertThat(testOutil.getNomOutil()).isEqualTo(UPDATED_NOM_OUTIL);
    }

    @Test
    @Transactional
    void patchNonExistingOutil() throws Exception {
        int databaseSizeBeforeUpdate = outilRepository.findAll().size();
        outil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, outil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(outil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOutil() throws Exception {
        int databaseSizeBeforeUpdate = outilRepository.findAll().size();
        outil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOutilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(outil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOutil() throws Exception {
        int databaseSizeBeforeUpdate = outilRepository.findAll().size();
        outil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOutilMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOutil() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        int databaseSizeBeforeDelete = outilRepository.findAll().size();

        // Delete the outil
        restOutilMockMvc
            .perform(delete(ENTITY_API_URL_ID, outil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
