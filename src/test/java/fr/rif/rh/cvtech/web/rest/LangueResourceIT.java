package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Langue;
import fr.rif.rh.cvtech.domain.enumeration.Language;
import fr.rif.rh.cvtech.repository.LangueRepository;
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
 * Integration tests for the {@link LangueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LangueResourceIT {

    private static final Language DEFAULT_LANGUE = Language.FRENCH;
    private static final Language UPDATED_LANGUE = Language.ENGLISH;

    private static final String ENTITY_API_URL = "/api/langues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LangueRepository langueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLangueMockMvc;

    private Langue langue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Langue createEntity(EntityManager em) {
        Langue langue = new Langue().langue(DEFAULT_LANGUE);
        return langue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Langue createUpdatedEntity(EntityManager em) {
        Langue langue = new Langue().langue(UPDATED_LANGUE);
        return langue;
    }

    @BeforeEach
    public void initTest() {
        langue = createEntity(em);
    }

    @Test
    @Transactional
    void createLangue() throws Exception {
        int databaseSizeBeforeCreate = langueRepository.findAll().size();
        // Create the Langue
        restLangueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isCreated());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeCreate + 1);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getLangue()).isEqualTo(DEFAULT_LANGUE);
    }

    @Test
    @Transactional
    void createLangueWithExistingId() throws Exception {
        // Create the Langue with an existing ID
        langue.setId(1L);

        int databaseSizeBeforeCreate = langueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLangueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLangues() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        // Get all the langueList
        restLangueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(langue.getId().intValue())))
            .andExpect(jsonPath("$.[*].langue").value(hasItem(DEFAULT_LANGUE.toString())));
    }

    @Test
    @Transactional
    void getLangue() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        // Get the langue
        restLangueMockMvc
            .perform(get(ENTITY_API_URL_ID, langue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(langue.getId().intValue()))
            .andExpect(jsonPath("$.langue").value(DEFAULT_LANGUE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLangue() throws Exception {
        // Get the langue
        restLangueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLangue() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeUpdate = langueRepository.findAll().size();

        // Update the langue
        Langue updatedLangue = langueRepository.findById(langue.getId()).get();
        // Disconnect from session so that the updates on updatedLangue are not directly saved in db
        em.detach(updatedLangue);
        updatedLangue.langue(UPDATED_LANGUE);

        restLangueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLangue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLangue))
            )
            .andExpect(status().isOk());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getLangue()).isEqualTo(UPDATED_LANGUE);
    }

    @Test
    @Transactional
    void putNonExistingLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, langue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLangueWithPatch() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeUpdate = langueRepository.findAll().size();

        // Update the langue using partial update
        Langue partialUpdatedLangue = new Langue();
        partialUpdatedLangue.setId(langue.getId());

        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLangue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLangue))
            )
            .andExpect(status().isOk());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getLangue()).isEqualTo(DEFAULT_LANGUE);
    }

    @Test
    @Transactional
    void fullUpdateLangueWithPatch() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeUpdate = langueRepository.findAll().size();

        // Update the langue using partial update
        Langue partialUpdatedLangue = new Langue();
        partialUpdatedLangue.setId(langue.getId());

        partialUpdatedLangue.langue(UPDATED_LANGUE);

        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLangue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLangue))
            )
            .andExpect(status().isOk());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getLangue()).isEqualTo(UPDATED_LANGUE);
    }

    @Test
    @Transactional
    void patchNonExistingLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, langue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLangue() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeDelete = langueRepository.findAll().size();

        // Delete the langue
        restLangueMockMvc
            .perform(delete(ENTITY_API_URL_ID, langue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
