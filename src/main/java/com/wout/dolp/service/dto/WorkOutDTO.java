package com.wout.dolp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WorkOut entity.
 */
public class WorkOutDTO implements Serializable {

    private Long id;

    private String name;

    private Long programId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkOutDTO workOutDTO = (WorkOutDTO) o;
        if(workOutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workOutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkOutDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
