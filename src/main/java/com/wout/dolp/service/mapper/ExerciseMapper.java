package com.wout.dolp.service.mapper;

import com.wout.dolp.domain.*;
import com.wout.dolp.service.dto.ExerciseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Exercise and its DTO ExerciseDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkOutMapper.class})
public interface ExerciseMapper extends EntityMapper<ExerciseDTO, Exercise> {

    @Mapping(source = "workOut.id", target = "workOutId")
    ExerciseDTO toDto(Exercise exercise);

    @Mapping(target = "setts", ignore = true)
    @Mapping(source = "workOutId", target = "workOut")
    Exercise toEntity(ExerciseDTO exerciseDTO);

    default Exercise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exercise exercise = new Exercise();
        exercise.setId(id);
        return exercise;
    }
}
