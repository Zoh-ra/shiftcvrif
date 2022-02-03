package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Avis;
import fr.rif.rh.cvtech.repository.AvisRepository;
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
 * Integration tests for the {@link AvisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvisResourceIT {

    private static final String DEFAULT_NOM_AVIS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_AVIS = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_AVIS = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_AVIS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_AVIS = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_AVIS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_AVIS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_AVIS = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_AVIS = "AAAAAAAAAA";
    private static final String UPDATED_DATE_AVIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/avis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisMockMvc;

    private Avis avis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avis createEntity(EntityManager em) {
        Avis avis = new Avis()
            .nomAvis(DEFAULT_NOM_AVIS)
            .prenomAvis(DEFAULT_PRENOM_AVIS)
            .photoAvis(DEFAULT_PHOTO_AVIS)
            .descriptionAvis(DEFAULT_DESCRIPTION_AVIS)
            .dateAvis(DEFAULT_DATE_AVIS);
        return avis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avis createUpdatedEntity(EntityManager em) {
        Avis avis = new Avis()
            .nomAvis(UPDATED_NOM_AVIS)
            .prenomAvis(UPDATED_PRENOM_AVIS)
            .photoAvis(UPDATED_PHOTO_AVIS)
            .descriptionAvis(UPDATED_DESCRIPTION_AVIS)
            .dateAvis(UPDATED_DATE_AVIS);
        return avis;
    }

    @BeforeEach
    public void initTest() {
        avis = createEntity(em);
    }

    @Test
    @Transactional
    void createAvis() throws Exception {
        int databaseSizeBeforeCreate = avisRepository.findAll().size();
        // Create the Avis
        restAvisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isCreated());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeCreate + 1);
        Avis testAvis = avisList.get(avisList.size() - 1);
        assertThat(testAvis.getNomAvis()).isEqualTo(DEFAULT_NOM_AVIS);
        assertThat(testAvis.getPrenomAvis()).isEqualTo(DEFAULT_PRENOM_AVIS);
        assertThat(testAvis.getPhotoAvis()).isEqualTo(DEFAULT_PHOTO_AVIS);
        assertThat(testAvis.getDescriptionAvis()).isEqualTo(DEFAULT_DESCRIPTION_AVIS);
        assertThat(testAvis.getDateAvis()).isEqualTo(DEFAULT_DATE_AVIS);
    }

    @Test
    @Transactional
    void createAvisWithExistingId() throws Exception {
        // Create the Avis with an existing ID
        avis.setId(1L);

        int databaseSizeBeforeCreate = avisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isBadRequest());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAvis() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        // Get all the avisList
        restAvisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avis.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomAvis").value(hasItem(DEFAULT_NOM_AVIS)))
            .andExpect(jsonPath("$.[*].prenomAvis").value(hasItem(DEFAULT_PRENOM_AVIS)))
            .andExpect(jsonPath("$.[*].photoAvis").value(hasItem(DEFAULT_PHOTO_AVIS)))
            .andExpect(jsonPath("$.[*].descriptionAvis").value(hasItem(DEFAULT_DESCRIPTION_AVIS)))
            .andExpect(jsonPath("$.[*].dateAvis").value(hasItem(DEFAULT_DATE_AVIS)));
    }

    @Test
    @Transactional
    void getAvis() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        // Get the avis
        restAvisMockMvc
            .perform(get(ENTITY_API_URL_ID, avis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avis.getId().intValue()))
            .andExpect(jsonPath("$.nomAvis").value(DEFAULT_NOM_AVIS))
            .andExpect(jsonPath("$.prenomAvis").value(DEFAULT_PRENOM_AVIS))
            .andExpect(jsonPath("$.photoAvis").value(DEFAULT_PHOTO_AVIS))
            .andExpect(jsonPath("$.descriptionAvis").value(DEFAULT_DESCRIPTION_AVIS))
            .andExpect(jsonPath("$.dateAvis").value(DEFAULT_DATE_AVIS));
    }

    @Test
    @Transactional
    void getNonExistingAvis() throws Exception {
        // Get the avis
        restAvisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAvis() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        int databaseSizeBeforeUpdate = avisRepository.findAll().size();

        // Update the avis
        Avis updatedAvis = avisRepository.findById(avis.getId()).get();
        // Disconnect from session so that the updates on updatedAvis are not directly saved in db
        em.detach(updatedAvis);
        updatedAvis
            .nomAvis(UPDATED_NOM_AVIS)
            .prenomAvis(UPDATED_PRENOM_AVIS)
            .photoAvis(UPDATED_PHOTO_AVIS)
            .descriptionAvis(UPDATED_DESCRIPTION_AVIS)
            .dateAvis(UPDATED_DATE_AVIS);

        restAvisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAvis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAvis))
            )
            .andExpect(status().isOk());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
        Avis testAvis = avisList.get(avisList.size() - 1);
        assertThat(testAvis.getNomAvis()).isEqualTo(UPDATED_NOM_AVIS);
        assertThat(testAvis.getPrenomAvis()).isEqualTo(UPDATED_PRENOM_AVIS);
        assertThat(testAvis.getPhotoAvis()).isEqualTo(UPDATED_PHOTO_AVIS);
        assertThat(testAvis.getDescriptionAvis()).isEqualTo(UPDATED_DESCRIPTION_AVIS);
        assertThat(testAvis.getDateAvis()).isEqualTo(UPDATED_DATE_AVIS);
    }

    @Test
    @Transactional
    void putNonExistingAvis() throws Exception {
        int databaseSizeBeforeUpdate = avisRepository.findAll().size();
        avis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvis() throws Exception {
        int databaseSizeBeforeUpdate = avisRepository.findAll().size();
        avis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvis() throws Exception {
        int databaseSizeBeforeUpdate = avisRepository.findAll().size();
        avis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvisWithPatch() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        int databaseSizeBeforeUpdate = avisRepository.findAll().size();

        // Update the avis using partial update
        Avis partialUpdatedAvis = new Avis();
        partialUpdatedAvis.setId(avis.getId());

        partialUpdatedAvis
            .nomAvis(UPDATED_NOM_AVIS)
            .prenomAvis(UPDATED_PRENOM_AVIS)
            .photoAvis(UPDATED_PHOTO_AVIS)
            .descriptionAvis(UPDATED_DESCRIPTION_AVIS)
            .dateAvis(UPDATED_DATE_AVIS);

        restAvisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvis))
            )
            .andExpect(status().isOk());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
        Avis testAvis = avisList.get(avisList.size() - 1);
        assertThat(testAvis.getNomAvis()).isEqualTo(UPDATED_NOM_AVIS);
        assertThat(testAvis.getPrenomAvis()).isEqualTo(UPDATED_PRENOM_AVIS);
        assertThat(testAvis.getPhotoAvis()).isEqualTo(UPDATED_PHOTO_AVIS);
        assertThat(testAvis.getDescriptionAvis()).isEqualTo(UPDATED_DESCRIPTION_AVIS);
        assertThat(testAvis.getDateAvis()).isEqualTo(UPDATED_DATE_AVIS);
    }

    @Test
    @Transactional
    void fullUpdateAvisWithPatch() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        int databaseSizeBeforeUpdate = avisRepository.findAll().size();

        // Update the avis using partial update
        Avis partialUpdatedAvis = new Avis();
        partialUpdatedAvis.setId(avis.getId());

        partialUpdatedAvis
            .nomAvis(UPDATED_NOM_AVIS)
            .prenomAvis(UPDATED_PRENOM_AVIS)
            .photoAvis(UPDATED_PHOTO_AVIS)
            .descriptionAvis(UPDATED_DESCRIPTION_AVIS)
            .dateAvis(UPDATED_DATE_AVIS);

        restAvisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvis))
            )
            .andExpect(status().isOk());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
        Avis testAvis = avisList.get(avisList.size() - 1);
        assertThat(testAvis.getNomAvis()).isEqualTo(UPDATED_NOM_AVIS);
        assertThat(testAvis.getPrenomAvis()).isEqualTo(UPDATED_PRENOM_AVIS);
        assertThat(testAvis.getPhotoAvis()).isEqualTo(UPDATED_PHOTO_AVIS);
        assertThat(testAvis.getDescriptionAvis()).isEqualTo(UPDATED_DESCRIPTION_AVIS);
        assertThat(testAvis.getDateAvis()).isEqualTo(UPDATED_DATE_AVIS);
    }

    @Test
    @Transactional
    void patchNonExistingAvis() throws Exception {
        int databaseSizeBeforeUpdate = avisRepository.findAll().size();
        avis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvis() throws Exception {
        int databaseSizeBeforeUpdate = avisRepository.findAll().size();
        avis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvis() throws Exception {
        int databaseSizeBeforeUpdate = avisRepository.findAll().size();
        avis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvis() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        int databaseSizeBeforeDelete = avisRepository.findAll().size();

        // Delete the avis
        restAvisMockMvc
            .perform(delete(ENTITY_API_URL_ID, avis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
