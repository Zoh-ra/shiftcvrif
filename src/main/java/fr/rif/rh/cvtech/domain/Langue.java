package fr.rif.rh.cvtech.domain;

import fr.rif.rh.cvtech.domain.enumeration.Language;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Langue.
 */
@Entity
@Table(name = "langue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Langue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "langue")
    private Language langue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Langue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLangue() {
        return this.langue;
    }

    public Langue langue(Language langue) {
        this.setLangue(langue);
        return this;
    }

    public void setLangue(Language langue) {
        this.langue = langue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Langue)) {
            return false;
        }
        return id != null && id.equals(((Langue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Langue{" +
            "id=" + getId() +
            ", langue='" + getLangue() + "'" +
            "}";
    }
}
