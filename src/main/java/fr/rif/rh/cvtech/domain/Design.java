package fr.rif.rh.cvtech.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Design.
 */
@Entity
@Table(name = "design")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Design implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_design")
    private String nomDesign;

    @Column(name = "taux_de_design")
    private Integer tauxDeDesign;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Design id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDesign() {
        return this.nomDesign;
    }

    public Design nomDesign(String nomDesign) {
        this.setNomDesign(nomDesign);
        return this;
    }

    public void setNomDesign(String nomDesign) {
        this.nomDesign = nomDesign;
    }

    public Integer getTauxDeDesign() {
        return this.tauxDeDesign;
    }

    public Design tauxDeDesign(Integer tauxDeDesign) {
        this.setTauxDeDesign(tauxDeDesign);
        return this;
    }

    public void setTauxDeDesign(Integer tauxDeDesign) {
        this.tauxDeDesign = tauxDeDesign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Design)) {
            return false;
        }
        return id != null && id.equals(((Design) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Design{" +
            "id=" + getId() +
            ", nomDesign='" + getNomDesign() + "'" +
            ", tauxDeDesign=" + getTauxDeDesign() +
            "}";
    }
}
