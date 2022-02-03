package fr.rif.rh.cvtech.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Avis.
 */
@Entity
@Table(name = "avis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Avis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_avis")
    private String nomAvis;

    @Column(name = "prenom_avis")
    private String prenomAvis;

    @Column(name = "photo_avis")
    private String photoAvis;

    @Column(name = "description_avis")
    private String descriptionAvis;

    @Column(name = "date_avis")
    private String dateAvis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomAvis() {
        return this.nomAvis;
    }

    public Avis nomAvis(String nomAvis) {
        this.setNomAvis(nomAvis);
        return this;
    }

    public void setNomAvis(String nomAvis) {
        this.nomAvis = nomAvis;
    }

    public String getPrenomAvis() {
        return this.prenomAvis;
    }

    public Avis prenomAvis(String prenomAvis) {
        this.setPrenomAvis(prenomAvis);
        return this;
    }

    public void setPrenomAvis(String prenomAvis) {
        this.prenomAvis = prenomAvis;
    }

    public String getPhotoAvis() {
        return this.photoAvis;
    }

    public Avis photoAvis(String photoAvis) {
        this.setPhotoAvis(photoAvis);
        return this;
    }

    public void setPhotoAvis(String photoAvis) {
        this.photoAvis = photoAvis;
    }

    public String getDescriptionAvis() {
        return this.descriptionAvis;
    }

    public Avis descriptionAvis(String descriptionAvis) {
        this.setDescriptionAvis(descriptionAvis);
        return this;
    }

    public void setDescriptionAvis(String descriptionAvis) {
        this.descriptionAvis = descriptionAvis;
    }

    public String getDateAvis() {
        return this.dateAvis;
    }

    public Avis dateAvis(String dateAvis) {
        this.setDateAvis(dateAvis);
        return this;
    }

    public void setDateAvis(String dateAvis) {
        this.dateAvis = dateAvis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avis)) {
            return false;
        }
        return id != null && id.equals(((Avis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avis{" +
            "id=" + getId() +
            ", nomAvis='" + getNomAvis() + "'" +
            ", prenomAvis='" + getPrenomAvis() + "'" +
            ", photoAvis='" + getPhotoAvis() + "'" +
            ", descriptionAvis='" + getDescriptionAvis() + "'" +
            ", dateAvis='" + getDateAvis() + "'" +
            "}";
    }
}
