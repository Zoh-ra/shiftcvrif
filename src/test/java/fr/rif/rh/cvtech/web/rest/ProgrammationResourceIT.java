package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Programmation;
import fr.rif.rh.cvtech.repository.ProgrammationRepository;
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
 * Integration tests for the {@link ProgrammationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgrammationResourceIT {

    private static final String DEFAULT_NOM_LANGAGE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_LANGAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAUX_DE_LANGAGE = 1;
    private static final Integer UPDATED_TAUX_DE_LANGAGE = 2;

    private static final String ENTITY_API_URL = "/api/programmations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProgrammationRepository programmationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgrammationMockMvc;

    private Programmation programmation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programmation createEntity(EntityManager em) {
        Programmation programmation = new Programmation().nomLangage(DEFAULT_NOM_LANGAGE).tauxDeLangage(DEFAULT_TAUX_DE_LANGAGE);
        return programmation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programmation createUpdatedEntity(EntityManager em) {
        Programmation programmation = new Programmation().nomLangage(UPDATED_NOM_LANGAGE).tauxDeLangage(UPDATED_TAUX_DE_LANGAGE);
        return programmation;
    }

    @BeforeEach
    public void initTest() {
        programmation = createEntity(em);
    }

    @Test
    @Transactional
    void createProgrammation() throws Exception {
        int databaseSizeBeforeCreate = programmationRepository.findAll().size();
        // Create the Programmation
        restProgrammationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programmation)))
            .andExpect(status().isCreated());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeCreate + 1);
        Programmation testProgrammation = programmationList.get(programmationList.size() - 1);
        assertThat(testProgrammation.getNomLangage()).isEqualTo(DEFAULT_NOM_LANGAGE);
        assertThat(testProgrammation.getTauxDeLangage()).isEqualTo(DEFAULT_TAUX_DE_LANGAGE);
    }

    @Test
    @Transactional
    void createProgrammationWithExistingId() throws Exception {
        // Create the Programmation with an existing ID
        programmation.setId(1L);

        int databaseSizeBeforeCreate = programmationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programmation)))
            .andExpect(status().isBadRequest());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProgrammations() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        // Get all the programmationList
        restProgrammationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomLangage").value(hasItem(DEFAULT_NOM_LANGAGE)))
            .andExpect(jsonPath("$.[*].tauxDeLangage").value(hasItem(DEFAULT_TAUX_DE_LANGAGE)));
    }

    @Test
    @Transactional
    void getProgrammation() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        // Get the programmation
        restProgrammationMockMvc
            .perform(get(ENTITY_API_URL_ID, programmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programmation.getId().intValue()))
            .andExpect(jsonPath("$.nomLangage").value(DEFAULT_NOM_LANGAGE))
            .andExpect(jsonPath("$.tauxDeLangage").value(DEFAULT_TAUX_DE_LANGAGE));
    }

    @Test
    @Transactional
    void getNonExistingProgrammation() throws Exception {
        // Get the programmation
        restProgrammationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProgrammation() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();

        // Update the programmation
        Programmation updatedProgrammation = programmationRepository.findById(programmation.getId()).get();
        // Disconnect from session so that the updates on updatedProgrammation are not directly saved in db
        em.detach(updatedProgrammation);
        updatedProgrammation.nomLangage(UPDATED_NOM_LANGAGE).tauxDeLangage(UPDATED_TAUX_DE_LANGAGE);

        restProgrammationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgrammation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgrammation))
            )
            .andExpect(status().isOk());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
        Programmation testProgrammation = programmationList.get(programmationList.size() - 1);
        assertThat(testProgrammation.getNomLangage()).isEqualTo(UPDATED_NOM_LANGAGE);
        assertThat(testProgrammation.getTauxDeLangage()).isEqualTo(UPDATED_TAUX_DE_LANGAGE);
    }

    @Test
    @Transactional
    void putNonExistingProgrammation() throws Exception {
        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();
        programmation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programmation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgrammation() throws Exception {
        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();
        programmation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgrammation() throws Exception {
        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();
        programmation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programmation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgrammationWithPatch() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();

        // Update the programmation using partial update
        Programmation partialUpdatedProgrammation = new Programmation();
        partialUpdatedProgrammation.setId(programmation.getId());

        partialUpdatedProgrammation.nomLangage(UPDATED_NOM_LANGAGE);

        restProgrammationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgrammation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgrammation))
            )
            .andExpect(status().isOk());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
        Programmation testProgrammation = programmationList.get(programmationList.size() - 1);
        assertThat(testProgrammation.getNomLangage()).isEqualTo(UPDATED_NOM_LANGAGE);
        assertThat(testProgrammation.getTauxDeLangage()).isEqualTo(DEFAULT_TAUX_DE_LANGAGE);
    }

    @Test
    @Transactional
    void fullUpdateProgrammationWithPatch() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();

        // Update the programmation using partial update
        Programmation partialUpdatedProgrammation = new Programmation();
        partialUpdatedProgrammation.setId(programmation.getId());

        partialUpdatedProgrammation.nomLangage(UPDATED_NOM_LANGAGE).tauxDeLangage(UPDATED_TAUX_DE_LANGAGE);

        restProgrammationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgrammation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgrammation))
            )
            .andExpect(status().isOk());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
        Programmation testProgrammation = programmationList.get(programmationList.size() - 1);
        assertThat(testProgrammation.getNomLangage()).isEqualTo(UPDATED_NOM_LANGAGE);
        assertThat(testProgrammation.getTauxDeLangage()).isEqualTo(UPDATED_TAUX_DE_LANGAGE);
    }

    @Test
    @Transactional
    void patchNonExistingProgrammation() throws Exception {
        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();
        programmation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programmation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgrammation() throws Exception {
        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();
        programmation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgrammation() throws Exception {
        int databaseSizeBeforeUpdate = programmationRepository.findAll().size();
        programmation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(programmation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programmation in the database
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgrammation() throws Exception {
        // Initialize the database
        programmationRepository.saveAndFlush(programmation);

        int databaseSizeBeforeDelete = programmationRepository.findAll().size();

        // Delete the programmation
        restProgrammationMockMvc
            .perform(delete(ENTITY_API_URL_ID, programmation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Programmation> programmationList = programmationRepository.findAll();
        assertThat(programmationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
