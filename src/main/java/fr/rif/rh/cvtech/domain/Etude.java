package fr.rif.rh.cvtech.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Etude.
 */
@Entity
@Table(name = "etude")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_etude")
    private String nomEtude;

    @Column(name = "annee_etude")
    private Instant anneeEtude;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresseEtude;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etude id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEtude() {
        return this.nomEtude;
    }

    public Etude nomEtude(String nomEtude) {
        this.setNomEtude(nomEtude);
        return this;
    }

    public void setNomEtude(String nomEtude) {
        this.nomEtude = nomEtude;
    }

    public Instant getAnneeEtude() {
        return this.anneeEtude;
    }

    public Etude anneeEtude(Instant anneeEtude) {
        this.setAnneeEtude(anneeEtude);
        return this;
    }

    public void setAnneeEtude(Instant anneeEtude) {
        this.anneeEtude = anneeEtude;
    }

    public Adresse getAdresseEtude() {
        return this.adresseEtude;
    }

    public void setAdresseEtude(Adresse adresse) {
        this.adresseEtude = adresse;
    }

    public Etude adresseEtude(Adresse adresse) {
        this.setAdresseEtude(adresse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etude)) {
            return false;
        }
        return id != null && id.equals(((Etude) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etude{" +
            "id=" + getId() +
            ", nomEtude='" + getNomEtude() + "'" +
            ", anneeEtude='" + getAnneeEtude() + "'" +
            "}";
    }
}
