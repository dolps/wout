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

import com.wout.dolp.domain.Sett;
import com.wout.dolp.domain.*; // for static metamodels
import com.wout.dolp.repository.SettRepository;
import com.wout.dolp.service.dto.SettCriteria;

import com.wout.dolp.service.dto.SettDTO;
import com.wout.dolp.service.mapper.SettMapper;

/**
 * Service for executing complex queries for Sett entities in the database.
 * The main input is a {@link SettCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SettDTO} or a {@link Page} of {@link SettDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SettQueryService extends QueryService<Sett> {

    private final Logger log = LoggerFactory.getLogger(SettQueryService.class);


    private final SettRepository settRepository;

    private final SettMapper settMapper;

    public SettQueryService(SettRepository settRepository, SettMapper settMapper) {
        this.settRepository = settRepository;
        this.settMapper = settMapper;
    }

    /**
     * Return a {@link List} of {@link SettDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SettDTO> findByCriteria(SettCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Sett> specification = createSpecification(criteria);
        return settMapper.toDto(settRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SettDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SettDTO> findByCriteria(SettCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Sett> specification = createSpecification(criteria);
        final Page<Sett> result = settRepository.findAll(specification, page);
        return result.map(settMapper::toDto);
    }

    /**
     * Function to convert SettCriteria to a {@link Specifications}
     */
    private Specifications<Sett> createSpecification(SettCriteria criteria) {
        Specifications<Sett> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Sett_.id));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), Sett_.order));
            }
            if (criteria.getReps() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReps(), Sett_.reps));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Sett_.weight));
            }
            if (criteria.getExerciseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getExerciseId(), Sett_.exercise, Exercise_.id));
            }
        }
        return specification;
    }

}
