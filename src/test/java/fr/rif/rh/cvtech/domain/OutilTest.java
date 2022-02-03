package fr.rif.rh.cvtech.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.rif.rh.cvtech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OutilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outil.class);
        Outil outil1 = new Outil();
        outil1.setId(1L);
        Outil outil2 = new Outil();
        outil2.setId(outil1.getId());
        assertThat(outil1).isEqualTo(outil2);
        outil2.setId(2L);
        assertThat(outil1).isNotEqualTo(outil2);
        outil1.setId(null);
        assertThat(outil1).isNotEqualTo(outil2);
    }
}
