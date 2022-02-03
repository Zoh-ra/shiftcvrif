package fr.rif.rh.cvtech.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Resume.
 */
@Entity
@Table(name = "resume")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @OneToOne
    @JoinColumn(unique = true)
    private Portfolio porfolio;

    @OneToOne
    @JoinColumn(unique = true)
    private Programmation programmation;

    @OneToOne
    @JoinColumn(unique = true)
    private Profil profil;

    @OneToOne
    @JoinColumn(unique = true)
    private Design design;

    @JsonIgnoreProperties(value = { "adresseExperience", "outil" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Experience experience;

    @JsonIgnoreProperties(value = { "adresseEtude" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Etude etude;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @OneToOne
    @JoinColumn(unique = true)
    private Langue langue;

    @OneToOne
    @JoinColumn(unique = true)
    private Avis avis;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresse;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Resume id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public Resume titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public ZonedDateTime getDateCreation() {
        return this.dateCreation;
    }

    public Resume dateCreation(ZonedDateTime dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Portfolio getPorfolio() {
        return this.porfolio;
    }

    public void setPorfolio(Portfolio portfolio) {
        this.porfolio = portfolio;
    }

    public Resume porfolio(Portfolio portfolio) {
        this.setPorfolio(portfolio);
        return this;
    }

    public Programmation getProgrammation() {
        return this.programmation;
    }

    public void setProgrammation(Programmation programmation) {
        this.programmation = programmation;
    }

    public Resume programmation(Programmation programmation) {
        this.setProgrammation(programmation);
        return this;
    }

    public Profil getProfil() {
        return this.profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Resume profil(Profil profil) {
        this.setProfil(profil);
        return this;
    }

    public Design getDesign() {
        return this.design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public Resume design(Design design) {
        this.setDesign(design);
        return this;
    }

    public Experience getExperience() {
        return this.experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Resume experience(Experience experience) {
        this.setExperience(experience);
        return this;
    }

    public Etude getEtude() {
        return this.etude;
    }

    public void setEtude(Etude etude) {
        this.etude = etude;
    }

    public Resume etude(Etude etude) {
        this.setEtude(etude);
        return this;
    }

    public Contact getContact() {
        return this.contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Resume contact(Contact contact) {
        this.setContact(contact);
        return this;
    }

    public Langue getLangue() {
        return this.langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Resume langue(Langue langue) {
        this.setLangue(langue);
        return this;
    }

    public Avis getAvis() {
        return this.avis;
    }

    public void setAvis(Avis avis) {
        this.avis = avis;
    }

    public Resume avis(Avis avis) {
        this.setAvis(avis);
        return this;
    }

    public Adresse getAdresse() {
        return this.adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Resume adresse(Adresse adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resume user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resume)) {
            return false;
        }
        return id != null && id.equals(((Resume) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resume{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            "}";
    }
}
