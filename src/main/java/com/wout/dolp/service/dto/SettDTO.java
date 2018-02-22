package com.wout.dolp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Sett entity.
 */
public class SettDTO implements Serializable {

    private Long id;

    private Integer order;

    private Integer reps;

    private Integer weight;

    private Long exerciseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SettDTO settDTO = (SettDTO) o;
        if(settDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettDTO{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", reps=" + getReps() +
            ", weight=" + getWeight() +
            "}";
    }
}
