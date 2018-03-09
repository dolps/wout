package com.wout.dolp.service.mapper;

import com.wout.dolp.domain.*;
import com.wout.dolp.service.dto.ProgramDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Program and its DTO ProgramDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProgramMapper extends EntityMapper<ProgramDTO, Program> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ProgramDTO toDto(Program program);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "workOuts", ignore = true)
    Program toEntity(ProgramDTO programDTO);

    default Program fromId(Long id) {
        if (id == null) {
            return null;
        }
        Program program = new Program();
        program.setId(id);
        return program;
    }
}
