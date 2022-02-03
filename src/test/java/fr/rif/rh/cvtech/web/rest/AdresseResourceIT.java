package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Adresse;
import fr.rif.rh.cvtech.repository.AdresseRepository;
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
 * Integration tests for the {@link AdresseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresseResourceIT {

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_VILLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE_POSTALE = 1;
    private static final Integer UPDATED_CODE_POSTALE = 2;

    private static final String ENTITY_API_URL = "/api/adresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createEntity(EntityManager em) {
        Adresse adresse = new Adresse().adresse(DEFAULT_ADRESSE).nomVille(DEFAULT_NOM_VILLE).codePostale(DEFAULT_CODE_POSTALE);
        return adresse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createUpdatedEntity(EntityManager em) {
        Adresse adresse = new Adresse().adresse(UPDATED_ADRESSE).nomVille(UPDATED_NOM_VILLE).codePostale(UPDATED_CODE_POSTALE);
        return adresse;
    }

    @BeforeEach
    public void initTest() {
        adresse = createEntity(em);
    }

    @Test
    @Transactional
    void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();
        // Create the Adresse
        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresse)))
            .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testAdresse.getNomVille()).isEqualTo(DEFAULT_NOM_VILLE);
        assertThat(testAdresse.getCodePostale()).isEqualTo(DEFAULT_CODE_POSTALE);
    }

    @Test
    @Transactional
    void createAdresseWithExistingId() throws Exception {
        // Create the Adresse with an existing ID
        adresse.setId(1L);

        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresse)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList
        restAdresseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].nomVille").value(hasItem(DEFAULT_NOM_VILLE)))
            .andExpect(jsonPath("$.[*].codePostale").value(hasItem(DEFAULT_CODE_POSTALE)));
    }

    @Test
    @Transactional
    void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc
            .perform(get(ENTITY_API_URL_ID, adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.nomVille").value(DEFAULT_NOM_VILLE))
            .andExpect(jsonPath("$.codePostale").value(DEFAULT_CODE_POSTALE));
    }

    @Test
    @Transactional
    void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findById(adresse.getId()).get();
        // Disconnect from session so that the updates on updatedAdresse are not directly saved in db
        em.detach(updatedAdresse);
        updatedAdresse.adresse(UPDATED_ADRESSE).nomVille(UPDATED_NOM_VILLE).codePostale(UPDATED_CODE_POSTALE);

        restAdresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdresse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdresse))
            )
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAdresse.getNomVille()).isEqualTo(UPDATED_NOM_VILLE);
        assertThat(testAdresse.getCodePostale()).isEqualTo(UPDATED_CODE_POSTALE);
    }

    @Test
    @Transactional
    void putNonExistingAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();
        adresse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adresse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();
        adresse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adresse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();
        adresse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresseWithPatch() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse using partial update
        Adresse partialUpdatedAdresse = new Adresse();
        partialUpdatedAdresse.setId(adresse.getId());

        partialUpdatedAdresse.adresse(UPDATED_ADRESSE).nomVille(UPDATED_NOM_VILLE).codePostale(UPDATED_CODE_POSTALE);

        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdresse))
            )
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAdresse.getNomVille()).isEqualTo(UPDATED_NOM_VILLE);
        assertThat(testAdresse.getCodePostale()).isEqualTo(UPDATED_CODE_POSTALE);
    }

    @Test
    @Transactional
    void fullUpdateAdresseWithPatch() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse using partial update
        Adresse partialUpdatedAdresse = new Adresse();
        partialUpdatedAdresse.setId(adresse.getId());

        partialUpdatedAdresse.adresse(UPDATED_ADRESSE).nomVille(UPDATED_NOM_VILLE).codePostale(UPDATED_CODE_POSTALE);

        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdresse))
            )
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAdresse.getNomVille()).isEqualTo(UPDATED_NOM_VILLE);
        assertThat(testAdresse.getCodePostale()).isEqualTo(UPDATED_CODE_POSTALE);
    }

    @Test
    @Transactional
    void patchNonExistingAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();
        adresse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adresse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();
        adresse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adresse))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();
        adresse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adresse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Delete the adresse
        restAdresseMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
