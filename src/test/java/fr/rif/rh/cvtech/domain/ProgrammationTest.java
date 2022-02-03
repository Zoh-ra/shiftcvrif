package fr.rif.rh.cvtech.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.rif.rh.cvtech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgrammationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programmation.class);
        Programmation programmation1 = new Programmation();
        programmation1.setId(1L);
        Programmation programmation2 = new Programmation();
        programmation2.setId(programmation1.getId());
        assertThat(programmation1).isEqualTo(programmation2);
        programmation2.setId(2L);
        assertThat(programmation1).isNotEqualTo(programmation2);
        programmation1.setId(null);
        assertThat(programmation1).isNotEqualTo(programmation2);
    }
}
