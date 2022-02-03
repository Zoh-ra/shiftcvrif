package fr.rif.rh.cvtech.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Adresse.
 */
@Entity
@Table(name = "adresse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "nom_ville")
    private String nomVille;

    @Column(name = "code_postale")
    private Integer codePostale;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adresse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Adresse adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNomVille() {
        return this.nomVille;
    }

    public Adresse nomVille(String nomVille) {
        this.setNomVille(nomVille);
        return this;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    public Integer getCodePostale() {
        return this.codePostale;
    }

    public Adresse codePostale(Integer codePostale) {
        this.setCodePostale(codePostale);
        return this;
    }

    public void setCodePostale(Integer codePostale) {
        this.codePostale = codePostale;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresse)) {
            return false;
        }
        return id != null && id.equals(((Adresse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresse{" +
            "id=" + getId() +
            ", adresse='" + getAdresse() + "'" +
            ", nomVille='" + getNomVille() + "'" +
            ", codePostale=" + getCodePostale() +
            "}";
    }
}
