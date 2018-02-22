package com.wout.dolp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wout.dolp.service.WorkOutService;
import com.wout.dolp.web.rest.errors.BadRequestAlertException;
import com.wout.dolp.web.rest.util.HeaderUtil;
import com.wout.dolp.service.dto.WorkOutDTO;
import com.wout.dolp.service.dto.WorkOutCriteria;
import com.wout.dolp.service.WorkOutQueryService;
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
 * REST controller for managing WorkOut.
 */
@RestController
@RequestMapping("/api")
public class WorkOutResource {

    private final Logger log = LoggerFactory.getLogger(WorkOutResource.class);

    private static final String ENTITY_NAME = "workOut";

    private final WorkOutService workOutService;

    private final WorkOutQueryService workOutQueryService;

    public WorkOutResource(WorkOutService workOutService, WorkOutQueryService workOutQueryService) {
        this.workOutService = workOutService;
        this.workOutQueryService = workOutQueryService;
    }

    /**
     * POST  /work-outs : Create a new workOut.
     *
     * @param workOutDTO the workOutDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workOutDTO, or with status 400 (Bad Request) if the workOut has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-outs")
    @Timed
    public ResponseEntity<WorkOutDTO> createWorkOut(@RequestBody WorkOutDTO workOutDTO) throws URISyntaxException {
        log.debug("REST request to save WorkOut : {}", workOutDTO);
        if (workOutDTO.getId() != null) {
            throw new BadRequestAlertException("A new workOut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkOutDTO result = workOutService.save(workOutDTO);
        return ResponseEntity.created(new URI("/api/work-outs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-outs : Updates an existing workOut.
     *
     * @param workOutDTO the workOutDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workOutDTO,
     * or with status 400 (Bad Request) if the workOutDTO is not valid,
     * or with status 500 (Internal Server Error) if the workOutDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-outs")
    @Timed
    public ResponseEntity<WorkOutDTO> updateWorkOut(@RequestBody WorkOutDTO workOutDTO) throws URISyntaxException {
        log.debug("REST request to update WorkOut : {}", workOutDTO);
        if (workOutDTO.getId() == null) {
            return createWorkOut(workOutDTO);
        }
        WorkOutDTO result = workOutService.save(workOutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workOutDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-outs : get all the workOuts.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of workOuts in body
     */
    @GetMapping("/work-outs")
    @Timed
    public ResponseEntity<List<WorkOutDTO>> getAllWorkOuts(WorkOutCriteria criteria) {
        log.debug("REST request to get WorkOuts by criteria: {}", criteria);
        List<WorkOutDTO> entityList = workOutQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /work-outs/:id : get the "id" workOut.
     *
     * @param id the id of the workOutDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workOutDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-outs/{id}")
    @Timed
    public ResponseEntity<WorkOutDTO> getWorkOut(@PathVariable Long id) {
        log.debug("REST request to get WorkOut : {}", id);
        WorkOutDTO workOutDTO = workOutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workOutDTO));
    }

    /**
     * DELETE  /work-outs/:id : delete the "id" workOut.
     *
     * @param id the id of the workOutDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-outs/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkOut(@PathVariable Long id) {
        log.debug("REST request to delete WorkOut : {}", id);
        workOutService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
