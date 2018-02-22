package com.wout.dolp.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Exercise entity. This class is used in ExerciseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /exercises?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExerciseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private LongFilter settId;

    private LongFilter workOutId;

    public ExerciseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getSettId() {
        return settId;
    }

    public void setSettId(LongFilter settId) {
        this.settId = settId;
    }

    public LongFilter getWorkOutId() {
        return workOutId;
    }

    public void setWorkOutId(LongFilter workOutId) {
        this.workOutId = workOutId;
    }

    @Override
    public String toString() {
        return "ExerciseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (settId != null ? "settId=" + settId + ", " : "") +
                (workOutId != null ? "workOutId=" + workOutId + ", " : "") +
            "}";
    }

}
