package fr.rif.rh.cvtech.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Programmation.
 */
@Entity
@Table(name = "programmation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Programmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_langage")
    private String nomLangage;

    @Column(name = "taux_de_langage")
    private Integer tauxDeLangage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Programmation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomLangage() {
        return this.nomLangage;
    }

    public Programmation nomLangage(String nomLangage) {
        this.setNomLangage(nomLangage);
        return this;
    }

    public void setNomLangage(String nomLangage) {
        this.nomLangage = nomLangage;
    }

    public Integer getTauxDeLangage() {
        return this.tauxDeLangage;
    }

    public Programmation tauxDeLangage(Integer tauxDeLangage) {
        this.setTauxDeLangage(tauxDeLangage);
        return this;
    }

    public void setTauxDeLangage(Integer tauxDeLangage) {
        this.tauxDeLangage = tauxDeLangage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programmation)) {
            return false;
        }
        return id != null && id.equals(((Programmation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Programmation{" +
            "id=" + getId() +
            ", nomLangage='" + getNomLangage() + "'" +
            ", tauxDeLangage=" + getTauxDeLangage() +
            "}";
    }
}
