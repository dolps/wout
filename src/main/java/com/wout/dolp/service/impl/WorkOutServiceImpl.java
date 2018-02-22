package com.wout.dolp.service.impl;

import com.wout.dolp.service.WorkOutService;
import com.wout.dolp.domain.WorkOut;
import com.wout.dolp.repository.WorkOutRepository;
import com.wout.dolp.service.dto.WorkOutDTO;
import com.wout.dolp.service.mapper.WorkOutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkOut.
 */
@Service
@Transactional
public class WorkOutServiceImpl implements WorkOutService {

    private final Logger log = LoggerFactory.getLogger(WorkOutServiceImpl.class);

    private final WorkOutRepository workOutRepository;

    private final WorkOutMapper workOutMapper;

    public WorkOutServiceImpl(WorkOutRepository workOutRepository, WorkOutMapper workOutMapper) {
        this.workOutRepository = workOutRepository;
        this.workOutMapper = workOutMapper;
    }

    /**
     * Save a workOut.
     *
     * @param workOutDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkOutDTO save(WorkOutDTO workOutDTO) {
        log.debug("Request to save WorkOut : {}", workOutDTO);
        WorkOut workOut = workOutMapper.toEntity(workOutDTO);
        workOut = workOutRepository.save(workOut);
        return workOutMapper.toDto(workOut);
    }

    /**
     * Get all the workOuts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkOutDTO> findAll() {
        log.debug("Request to get all WorkOuts");
        return workOutRepository.findAll().stream()
            .map(workOutMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one workOut by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkOutDTO findOne(Long id) {
        log.debug("Request to get WorkOut : {}", id);
        WorkOut workOut = workOutRepository.findOne(id);
        return workOutMapper.toDto(workOut);
    }

    /**
     * Delete the workOut by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkOut : {}", id);
        workOutRepository.delete(id);
    }
}
