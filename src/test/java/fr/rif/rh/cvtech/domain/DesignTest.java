package fr.rif.rh.cvtech.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.rif.rh.cvtech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesignTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Design.class);
        Design design1 = new Design();
        design1.setId(1L);
        Design design2 = new Design();
        design2.setId(design1.getId());
        assertThat(design1).isEqualTo(design2);
        design2.setId(2L);
        assertThat(design1).isNotEqualTo(design2);
        design1.setId(null);
        assertThat(design1).isNotEqualTo(design2);
    }
}
