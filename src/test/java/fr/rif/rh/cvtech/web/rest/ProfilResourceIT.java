package fr.rif.rh.cvtech.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.rif.rh.cvtech.IntegrationTest;
import fr.rif.rh.cvtech.domain.Profil;
import fr.rif.rh.cvtech.repository.ProfilRepository;
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
 * Integration tests for the {@link ProfilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfilResourceIT {

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TEL_RESUME = 1;
    private static final Integer UPDATED_TEL_RESUME = 2;

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfilMockMvc;

    private Profil profil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profil createEntity(EntityManager em) {
        Profil profil = new Profil()
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .telResume(DEFAULT_TEL_RESUME)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .profession(DEFAULT_PROFESSION)
            .website(DEFAULT_WEBSITE)
            .description(DEFAULT_DESCRIPTION);
        return profil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profil createUpdatedEntity(EntityManager em) {
        Profil profil = new Profil()
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .telResume(UPDATED_TEL_RESUME)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .profession(UPDATED_PROFESSION)
            .website(UPDATED_WEBSITE)
            .description(UPDATED_DESCRIPTION);
        return profil;
    }

    @BeforeEach
    public void initTest() {
        profil = createEntity(em);
    }

    @Test
    @Transactional
    void createProfil() throws Exception {
        int databaseSizeBeforeCreate = profilRepository.findAll().size();
        // Create the Profil
        restProfilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isCreated());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeCreate + 1);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testProfil.getTelResume()).isEqualTo(DEFAULT_TEL_RESUME);
        assertThat(testProfil.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testProfil.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testProfil.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testProfil.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testProfil.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testProfil.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testProfil.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createProfilWithExistingId() throws Exception {
        // Create the Profil with an existing ID
        profil.setId(1L);

        int databaseSizeBeforeCreate = profilRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isBadRequest());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = profilRepository.findAll().size();
        // set the field null
        profil.setDateNaissance(null);

        // Create the Profil, which fails.

        restProfilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isBadRequest());

        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProfils() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList
        restProfilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profil.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].telResume").value(hasItem(DEFAULT_TEL_RESUME)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get the profil
        restProfilMockMvc
            .perform(get(ENTITY_API_URL_ID, profil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profil.getId().intValue()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.telResume").value(DEFAULT_TEL_RESUME))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingProfil() throws Exception {
        // Get the profil
        restProfilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        int databaseSizeBeforeUpdate = profilRepository.findAll().size();

        // Update the profil
        Profil updatedProfil = profilRepository.findById(profil.getId()).get();
        // Disconnect from session so that the updates on updatedProfil are not directly saved in db
        em.detach(updatedProfil);
        updatedProfil
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .telResume(UPDATED_TEL_RESUME)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .profession(UPDATED_PROFESSION)
            .website(UPDATED_WEBSITE)
            .description(UPDATED_DESCRIPTION);

        restProfilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfil))
            )
            .andExpect(status().isOk());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testProfil.getTelResume()).isEqualTo(UPDATED_TEL_RESUME);
        assertThat(testProfil.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testProfil.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testProfil.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testProfil.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testProfil.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testProfil.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testProfil.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();
        profil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();
        profil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();
        profil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfilWithPatch() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        int databaseSizeBeforeUpdate = profilRepository.findAll().size();

        // Update the profil using partial update
        Profil partialUpdatedProfil = new Profil();
        partialUpdatedProfil.setId(profil.getId());

        partialUpdatedProfil.telResume(UPDATED_TEL_RESUME).addressLine2(UPDATED_ADDRESS_LINE_2).country(UPDATED_COUNTRY);

        restProfilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfil))
            )
            .andExpect(status().isOk());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testProfil.getTelResume()).isEqualTo(UPDATED_TEL_RESUME);
        assertThat(testProfil.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testProfil.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testProfil.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testProfil.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testProfil.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testProfil.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testProfil.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateProfilWithPatch() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        int databaseSizeBeforeUpdate = profilRepository.findAll().size();

        // Update the profil using partial update
        Profil partialUpdatedProfil = new Profil();
        partialUpdatedProfil.setId(profil.getId());

        partialUpdatedProfil
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .telResume(UPDATED_TEL_RESUME)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .profession(UPDATED_PROFESSION)
            .website(UPDATED_WEBSITE)
            .description(UPDATED_DESCRIPTION);

        restProfilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfil))
            )
            .andExpect(status().isOk());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testProfil.getTelResume()).isEqualTo(UPDATED_TEL_RESUME);
        assertThat(testProfil.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testProfil.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testProfil.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testProfil.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testProfil.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testProfil.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testProfil.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();
        profil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();
        profil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profil))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();
        profil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfilMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        int databaseSizeBeforeDelete = profilRepository.findAll().size();

        // Delete the profil
        restProfilMockMvc
            .perform(delete(ENTITY_API_URL_ID, profil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
