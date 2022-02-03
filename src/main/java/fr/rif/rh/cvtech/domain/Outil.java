package fr.rif.rh.cvtech.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Outil.
 */
@Entity
@Table(name = "outil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Outil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_outil")
    private String nomOutil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Outil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomOutil() {
        return this.nomOutil;
    }

    public Outil nomOutil(String nomOutil) {
        this.setNomOutil(nomOutil);
        return this;
    }

    public void setNomOutil(String nomOutil) {
        this.nomOutil = nomOutil;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Outil)) {
            return false;
        }
        return id != null && id.equals(((Outil) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Outil{" +
            "id=" + getId() +
            ", nomOutil='" + getNomOutil() + "'" +
            "}";
    }
}
