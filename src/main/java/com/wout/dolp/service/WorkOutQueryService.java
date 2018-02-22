package com.wout.dolp.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.wout.dolp.domain.WorkOut;
import com.wout.dolp.domain.*; // for static metamodels
import com.wout.dolp.repository.WorkOutRepository;
import com.wout.dolp.service.dto.WorkOutCriteria;

import com.wout.dolp.service.dto.WorkOutDTO;
import com.wout.dolp.service.mapper.WorkOutMapper;

/**
 * Service for executing complex queries for WorkOut entities in the database.
 * The main input is a {@link WorkOutCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkOutDTO} or a {@link Page} of {@link WorkOutDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkOutQueryService extends QueryService<WorkOut> {

    private final Logger log = LoggerFactory.getLogger(WorkOutQueryService.class);


    private final WorkOutRepository workOutRepository;

    private final WorkOutMapper workOutMapper;

    public WorkOutQueryService(WorkOutRepository workOutRepository, WorkOutMapper workOutMapper) {
        this.workOutRepository = workOutRepository;
        this.workOutMapper = workOutMapper;
    }

    /**
     * Return a {@link List} of {@link WorkOutDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkOutDTO> findByCriteria(WorkOutCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<WorkOut> specification = createSpecification(criteria);
        return workOutMapper.toDto(workOutRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkOutDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkOutDTO> findByCriteria(WorkOutCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<WorkOut> specification = createSpecification(criteria);
        final Page<WorkOut> result = workOutRepository.findAll(specification, page);
        return result.map(workOutMapper::toDto);
    }

    /**
     * Function to convert WorkOutCriteria to a {@link Specifications}
     */
    private Specifications<WorkOut> createSpecification(WorkOutCriteria criteria) {
        Specifications<WorkOut> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), WorkOut_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), WorkOut_.name));
            }
            if (criteria.getProgramId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProgramId(), WorkOut_.program, Program_.id));
            }
        }
        return specification;
    }

}
