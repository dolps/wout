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
 * Criteria class for the Sett entity. This class is used in SettResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /setts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SettCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter order;

    private IntegerFilter reps;

    private IntegerFilter weight;

    private LongFilter exerciseId;

    public SettCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public IntegerFilter getReps() {
        return reps;
    }

    public void setReps(IntegerFilter reps) {
        this.reps = reps;
    }

    public IntegerFilter getWeight() {
        return weight;
    }

    public void setWeight(IntegerFilter weight) {
        this.weight = weight;
    }

    public LongFilter getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(LongFilter exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public String toString() {
        return "SettCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (reps != null ? "reps=" + reps + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (exerciseId != null ? "exerciseId=" + exerciseId + ", " : "") +
            "}";
    }

}
