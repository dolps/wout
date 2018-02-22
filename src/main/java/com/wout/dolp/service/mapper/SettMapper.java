package com.wout.dolp.service.mapper;

import com.wout.dolp.domain.*;
import com.wout.dolp.service.dto.SettDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sett and its DTO SettDTO.
 */
@Mapper(componentModel = "spring", uses = {ExerciseMapper.class})
public interface SettMapper extends EntityMapper<SettDTO, Sett> {

    @Mapping(source = "exercise.id", target = "exerciseId")
    SettDTO toDto(Sett sett);

    @Mapping(source = "exerciseId", target = "exercise")
    Sett toEntity(SettDTO settDTO);

    default Sett fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sett sett = new Sett();
        sett.setId(id);
        return sett;
    }
}
