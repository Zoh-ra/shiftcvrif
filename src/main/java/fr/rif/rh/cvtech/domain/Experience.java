package fr.rif.rh.cvtech.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Experience.
 */
@Entity
@Table(name = "experience")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_entreprise")
    private String nomEntreprise;

    @Column(name = "nom_poste")
    private String nomPoste;

    @Column(name = "date_experience")
    private Instant dateExperience;

    @Column(name = "description_experience")
    private String descriptionExperience;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresseExperience;

    @OneToOne
    @JoinColumn(unique = true)
    private Outil outil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Experience id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEntreprise() {
        return this.nomEntreprise;
    }

    public Experience nomEntreprise(String nomEntreprise) {
        this.setNomEntreprise(nomEntreprise);
        return this;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getNomPoste() {
        return this.nomPoste;
    }

    public Experience nomPoste(String nomPoste) {
        this.setNomPoste(nomPoste);
        return this;
    }

    public void setNomPoste(String nomPoste) {
        this.nomPoste = nomPoste;
    }

    public Instant getDateExperience() {
        return this.dateExperience;
    }

    public Experience dateExperience(Instant dateExperience) {
        this.setDateExperience(dateExperience);
        return this;
    }

    public void setDateExperience(Instant dateExperience) {
        this.dateExperience = dateExperience;
    }

    public String getDescriptionExperience() {
        return this.descriptionExperience;
    }

    public Experience descriptionExperience(String descriptionExperience) {
        this.setDescriptionExperience(descriptionExperience);
        return this;
    }

    public void setDescriptionExperience(String descriptionExperience) {
        this.descriptionExperience = descriptionExperience;
    }

    public Adresse getAdresseExperience() {
        return this.adresseExperience;
    }

    public void setAdresseExperience(Adresse adresse) {
        this.adresseExperience = adresse;
    }

    public Experience adresseExperience(Adresse adresse) {
        this.setAdresseExperience(adresse);
        return this;
    }

    public Outil getOutil() {
        return this.outil;
    }

    public void setOutil(Outil outil) {
        this.outil = outil;
    }

    public Experience outil(Outil outil) {
        this.setOutil(outil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Experience)) {
            return false;
        }
        return id != null && id.equals(((Experience) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Experience{" +
            "id=" + getId() +
            ", nomEntreprise='" + getNomEntreprise() + "'" +
            ", nomPoste='" + getNomPoste() + "'" +
            ", dateExperience='" + getDateExperience() + "'" +
            ", descriptionExperience='" + getDescriptionExperience() + "'" +
            "}";
    }
}
