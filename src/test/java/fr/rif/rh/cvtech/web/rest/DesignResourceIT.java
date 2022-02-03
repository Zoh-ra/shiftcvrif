package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Design;
import fr.rif.rh.cvtech.repository.DesignRepository;
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
 * Integration tests for the {@link DesignResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesignResourceIT {

    private static final String DEFAULT_NOM_DESIGN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DESIGN = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAUX_DE_DESIGN = 1;
    private static final Integer UPDATED_TAUX_DE_DESIGN = 2;

    private static final String ENTITY_API_URL = "/api/designs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesignRepository designRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesignMockMvc;

    private Design design;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Design createEntity(EntityManager em) {
        Design design = new Design().nomDesign(DEFAULT_NOM_DESIGN).tauxDeDesign(DEFAULT_TAUX_DE_DESIGN);
        return design;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Design createUpdatedEntity(EntityManager em) {
        Design design = new Design().nomDesign(UPDATED_NOM_DESIGN).tauxDeDesign(UPDATED_TAUX_DE_DESIGN);
        return design;
    }

    @BeforeEach
    public void initTest() {
        design = createEntity(em);
    }

    @Test
    @Transactional
    void createDesign() throws Exception {
        int databaseSizeBeforeCreate = designRepository.findAll().size();
        // Create the Design
        restDesignMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(design)))
            .andExpect(status().isCreated());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeCreate + 1);
        Design testDesign = designList.get(designList.size() - 1);
        assertThat(testDesign.getNomDesign()).isEqualTo(DEFAULT_NOM_DESIGN);
        assertThat(testDesign.getTauxDeDesign()).isEqualTo(DEFAULT_TAUX_DE_DESIGN);
    }

    @Test
    @Transactional
    void createDesignWithExistingId() throws Exception {
        // Create the Design with an existing ID
        design.setId(1L);

        int databaseSizeBeforeCreate = designRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(design)))
            .andExpect(status().isBadRequest());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDesigns() throws Exception {
        // Initialize the database
        designRepository.saveAndFlush(design);

        // Get all the designList
        restDesignMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(design.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomDesign").value(hasItem(DEFAULT_NOM_DESIGN)))
            .andExpect(jsonPath("$.[*].tauxDeDesign").value(hasItem(DEFAULT_TAUX_DE_DESIGN)));
    }

    @Test
    @Transactional
    void getDesign() throws Exception {
        // Initialize the database
        designRepository.saveAndFlush(design);

        // Get the design
        restDesignMockMvc
            .perform(get(ENTITY_API_URL_ID, design.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(design.getId().intValue()))
            .andExpect(jsonPath("$.nomDesign").value(DEFAULT_NOM_DESIGN))
            .andExpect(jsonPath("$.tauxDeDesign").value(DEFAULT_TAUX_DE_DESIGN));
    }

    @Test
    @Transactional
    void getNonExistingDesign() throws Exception {
        // Get the design
        restDesignMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDesign() throws Exception {
        // Initialize the database
        designRepository.saveAndFlush(design);

        int databaseSizeBeforeUpdate = designRepository.findAll().size();

        // Update the design
        Design updatedDesign = designRepository.findById(design.getId()).get();
        // Disconnect from session so that the updates on updatedDesign are not directly saved in db
        em.detach(updatedDesign);
        updatedDesign.nomDesign(UPDATED_NOM_DESIGN).tauxDeDesign(UPDATED_TAUX_DE_DESIGN);

        restDesignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDesign.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDesign))
            )
            .andExpect(status().isOk());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
        Design testDesign = designList.get(designList.size() - 1);
        assertThat(testDesign.getNomDesign()).isEqualTo(UPDATED_NOM_DESIGN);
        assertThat(testDesign.getTauxDeDesign()).isEqualTo(UPDATED_TAUX_DE_DESIGN);
    }

    @Test
    @Transactional
    void putNonExistingDesign() throws Exception {
        int databaseSizeBeforeUpdate = designRepository.findAll().size();
        design.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, design.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(design))
            )
            .andExpect(status().isBadRequest());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesign() throws Exception {
        int databaseSizeBeforeUpdate = designRepository.findAll().size();
        design.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(design))
            )
            .andExpect(status().isBadRequest());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesign() throws Exception {
        int databaseSizeBeforeUpdate = designRepository.findAll().size();
        design.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(design)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesignWithPatch() throws Exception {
        // Initialize the database
        designRepository.saveAndFlush(design);

        int databaseSizeBeforeUpdate = designRepository.findAll().size();

        // Update the design using partial update
        Design partialUpdatedDesign = new Design();
        partialUpdatedDesign.setId(design.getId());

        partialUpdatedDesign.tauxDeDesign(UPDATED_TAUX_DE_DESIGN);

        restDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesign.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesign))
            )
            .andExpect(status().isOk());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
        Design testDesign = designList.get(designList.size() - 1);
        assertThat(testDesign.getNomDesign()).isEqualTo(DEFAULT_NOM_DESIGN);
        assertThat(testDesign.getTauxDeDesign()).isEqualTo(UPDATED_TAUX_DE_DESIGN);
    }

    @Test
    @Transactional
    void fullUpdateDesignWithPatch() throws Exception {
        // Initialize the database
        designRepository.saveAndFlush(design);

        int databaseSizeBeforeUpdate = designRepository.findAll().size();

        // Update the design using partial update
        Design partialUpdatedDesign = new Design();
        partialUpdatedDesign.setId(design.getId());

        partialUpdatedDesign.nomDesign(UPDATED_NOM_DESIGN).tauxDeDesign(UPDATED_TAUX_DE_DESIGN);

        restDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesign.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesign))
            )
            .andExpect(status().isOk());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
        Design testDesign = designList.get(designList.size() - 1);
        assertThat(testDesign.getNomDesign()).isEqualTo(UPDATED_NOM_DESIGN);
        assertThat(testDesign.getTauxDeDesign()).isEqualTo(UPDATED_TAUX_DE_DESIGN);
    }

    @Test
    @Transactional
    void patchNonExistingDesign() throws Exception {
        int databaseSizeBeforeUpdate = designRepository.findAll().size();
        design.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, design.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(design))
            )
            .andExpect(status().isBadRequest());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesign() throws Exception {
        int databaseSizeBeforeUpdate = designRepository.findAll().size();
        design.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(design))
            )
            .andExpect(status().isBadRequest());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesign() throws Exception {
        int databaseSizeBeforeUpdate = designRepository.findAll().size();
        design.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(design)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Design in the database
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesign() throws Exception {
        // Initialize the database
        designRepository.saveAndFlush(design);

        int databaseSizeBeforeDelete = designRepository.findAll().size();

        // Delete the design
        restDesignMockMvc
            .perform(delete(ENTITY_API_URL_ID, design.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Design> designList = designRepository.findAll();
        assertThat(designList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
