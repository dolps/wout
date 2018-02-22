package com.wout.dolp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "exercise")
    @JsonIgnore
    private Set<Sett> setts = new HashSet<>();

    @ManyToOne
    private WorkOut workOut;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Exercise name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sett> getSetts() {
        return setts;
    }

    public Exercise setts(Set<Sett> setts) {
        this.setts = setts;
        return this;
    }

    public Exercise addSett(Sett sett) {
        this.setts.add(sett);
        sett.setExercise(this);
        return this;
    }

    public Exercise removeSett(Sett sett) {
        this.setts.remove(sett);
        sett.setExercise(null);
        return this;
    }

    public void setSetts(Set<Sett> setts) {
        this.setts = setts;
    }

    public WorkOut getWorkOut() {
        return workOut;
    }

    public Exercise workOut(WorkOut workOut) {
        this.workOut = workOut;
        return this;
    }

    public void setWorkOut(WorkOut workOut) {
        this.workOut = workOut;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        if (exercise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exercise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
