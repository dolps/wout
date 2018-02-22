package com.wout.dolp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wout.dolp.service.SettService;
import com.wout.dolp.web.rest.errors.BadRequestAlertException;
import com.wout.dolp.web.rest.util.HeaderUtil;
import com.wout.dolp.service.dto.SettDTO;
import com.wout.dolp.service.dto.SettCriteria;
import com.wout.dolp.service.SettQueryService;
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
 * REST controller for managing Sett.
 */
@RestController
@RequestMapping("/api")
public class SettResource {

    private final Logger log = LoggerFactory.getLogger(SettResource.class);

    private static final String ENTITY_NAME = "sett";

    private final SettService settService;

    private final SettQueryService settQueryService;

    public SettResource(SettService settService, SettQueryService settQueryService) {
        this.settService = settService;
        this.settQueryService = settQueryService;
    }

    /**
     * POST  /setts : Create a new sett.
     *
     * @param settDTO the settDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new settDTO, or with status 400 (Bad Request) if the sett has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/setts")
    @Timed
    public ResponseEntity<SettDTO> createSett(@RequestBody SettDTO settDTO) throws URISyntaxException {
        log.debug("REST request to save Sett : {}", settDTO);
        if (settDTO.getId() != null) {
            throw new BadRequestAlertException("A new sett cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SettDTO result = settService.save(settDTO);
        return ResponseEntity.created(new URI("/api/setts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setts : Updates an existing sett.
     *
     * @param settDTO the settDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated settDTO,
     * or with status 400 (Bad Request) if the settDTO is not valid,
     * or with status 500 (Internal Server Error) if the settDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/setts")
    @Timed
    public ResponseEntity<SettDTO> updateSett(@RequestBody SettDTO settDTO) throws URISyntaxException {
        log.debug("REST request to update Sett : {}", settDTO);
        if (settDTO.getId() == null) {
            return createSett(settDTO);
        }
        SettDTO result = settService.save(settDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, settDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setts : get all the setts.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of setts in body
     */
    @GetMapping("/setts")
    @Timed
    public ResponseEntity<List<SettDTO>> getAllSetts(SettCriteria criteria) {
        log.debug("REST request to get Setts by criteria: {}", criteria);
        List<SettDTO> entityList = settQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /setts/:id : get the "id" sett.
     *
     * @param id the id of the settDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the settDTO, or with status 404 (Not Found)
     */
    @GetMapping("/setts/{id}")
    @Timed
    public ResponseEntity<SettDTO> getSett(@PathVariable Long id) {
        log.debug("REST request to get Sett : {}", id);
        SettDTO settDTO = settService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(settDTO));
    }

    /**
     * DELETE  /setts/:id : delete the "id" sett.
     *
     * @param id the id of the settDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/setts/{id}")
    @Timed
    public ResponseEntity<Void> deleteSett(@PathVariable Long id) {
        log.debug("REST request to delete Sett : {}", id);
        settService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
