package fr.rif.rh.cvtech.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.rif.rh.cvtech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profil.class);
        Profil profil1 = new Profil();
        profil1.setId(1L);
        Profil profil2 = new Profil();
        profil2.setId(profil1.getId());
        assertThat(profil1).isEqualTo(profil2);
        profil2.setId(2L);
        assertThat(profil1).isNotEqualTo(profil2);
        profil1.setId(null);
        assertThat(profil1).isNotEqualTo(profil2);
    }
}
