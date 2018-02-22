package com.wout.dolp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wout.dolp.service.ExerciseService;
import com.wout.dolp.web.rest.errors.BadRequestAlertException;
import com.wout.dolp.web.rest.util.HeaderUtil;
import com.wout.dolp.service.dto.ExerciseDTO;
import com.wout.dolp.service.dto.ExerciseCriteria;
import com.wout.dolp.service.ExerciseQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Exercise.
 */
@RestController
@RequestMapping("/api")
public class ExerciseResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseResource.class);

    private static final String ENTITY_NAME = "exercise";

    private final ExerciseService exerciseService;

    private final ExerciseQueryService exerciseQueryService;

    public ExerciseResource(ExerciseService exerciseService, ExerciseQueryService exerciseQueryService) {
        this.exerciseService = exerciseService;
        this.exerciseQueryService = exerciseQueryService;
    }

    /**
     * POST  /exercises : Create a new exercise.
     *
     * @param exerciseDTO the exerciseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseDTO, or with status 400 (Bad Request) if the exercise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercises")
    @Timed
    public ResponseEntity<ExerciseDTO> createExercise(@RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to save Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() != null) {
            throw new BadRequestAlertException("A new exercise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.created(new URI("/api/exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercises : Updates an existing exercise.
     *
     * @param exerciseDTO the exerciseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseDTO,
     * or with status 400 (Bad Request) if the exerciseDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercises")
    @Timed
    public ResponseEntity<ExerciseDTO> updateExercise(@RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to update Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() == null) {
            return createExercise(exerciseDTO);
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exerciseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercises : get all the exercises.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of exercises in body
     */
    @GetMapping("/exercises")
    @Timed
    public ResponseEntity<List<ExerciseDTO>> getAllExercises(ExerciseCriteria criteria) {
        log.debug("REST request to get Exercises by criteria: {}", criteria);
        List<ExerciseDTO> entityList = exerciseQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /exercises/:id : get the "id" exercise.
     *
     * @param id the id of the exerciseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercises/{id}")
    @Timed
    public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
        log.debug("REST request to get Exercise : {}", id);
        ExerciseDTO exerciseDTO = exerciseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseDTO));
    }

    /**
     * DELETE  /exercises/:id : delete the "id" exercise.
     *
     * @param id the id of the exerciseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercises/{id}")
    @Timed
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        log.debug("REST request to delete Exercise : {}", id);
        exerciseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
