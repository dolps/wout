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

import com.wout.dolp.domain.Exercise;
import com.wout.dolp.domain.*; // for static metamodels
import com.wout.dolp.repository.ExerciseRepository;
import com.wout.dolp.service.dto.ExerciseCriteria;

import com.wout.dolp.service.dto.ExerciseDTO;
import com.wout.dolp.service.mapper.ExerciseMapper;

/**
 * Service for executing complex queries for Exercise entities in the database.
 * The main input is a {@link ExerciseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExerciseDTO} or a {@link Page} of {@link ExerciseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExerciseQueryService extends QueryService<Exercise> {

    private final Logger log = LoggerFactory.getLogger(ExerciseQueryService.class);


    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    public ExerciseQueryService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    /**
     * Return a {@link List} of {@link ExerciseDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExerciseDTO> findByCriteria(ExerciseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Exercise> specification = createSpecification(criteria);
        return exerciseMapper.toDto(exerciseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExerciseDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExerciseDTO> findByCriteria(ExerciseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Exercise> specification = createSpecification(criteria);
        final Page<Exercise> result = exerciseRepository.findAll(specification, page);
        return result.map(exerciseMapper::toDto);
    }

    /**
     * Function to convert ExerciseCriteria to a {@link Specifications}
     */
    private Specifications<Exercise> createSpecification(ExerciseCriteria criteria) {
        Specifications<Exercise> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Exercise_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Exercise_.name));
            }
            if (criteria.getSettId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSettId(), Exercise_.setts, Sett_.id));
            }
            if (criteria.getWorkOutId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getWorkOutId(), Exercise_.workOut, WorkOut_.id));
            }
        }
        return specification;
    }

}
