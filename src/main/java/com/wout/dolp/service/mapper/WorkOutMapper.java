package com.wout.dolp.service.mapper;

import com.wout.dolp.domain.*;
import com.wout.dolp.service.dto.WorkOutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WorkOut and its DTO WorkOutDTO.
 */
@Mapper(componentModel = "spring", uses = {ProgramMapper.class})
public interface WorkOutMapper extends EntityMapper<WorkOutDTO, WorkOut> {

    @Mapping(source = "program.id", target = "programId")
    WorkOutDTO toDto(WorkOut workOut);

    @Mapping(source = "programId", target = "program")
    WorkOut toEntity(WorkOutDTO workOutDTO);

    default WorkOut fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkOut workOut = new WorkOut();
        workOut.setId(id);
        return workOut;
    }
}
