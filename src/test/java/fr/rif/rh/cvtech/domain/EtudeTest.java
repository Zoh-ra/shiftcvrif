package fr.rif.rh.cvtech.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.rif.rh.cvtech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtudeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etude.class);
        Etude etude1 = new Etude();
        etude1.setId(1L);
        Etude etude2 = new Etude();
        etude2.setId(etude1.getId());
        assertThat(etude1).isEqualTo(etude2);
        etude2.setId(2L);
        assertThat(etude1).isNotEqualTo(etude2);
        etude1.setId(null);
        assertThat(etude1).isNotEqualTo(etude2);
    }
}
