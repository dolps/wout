package com.wout.dolp.service;

import com.wout.dolp.service.dto.WorkOutDTO;
import java.util.List;

/**
 * Service Interface for managing WorkOut.
 */
public interface WorkOutService {

    /**
     * Save a workOut.
     *
     * @param workOutDTO the entity to save
     * @return the persisted entity
     */
    WorkOutDTO save(WorkOutDTO workOutDTO);

    /**
     * Get all the workOuts.
     *
     * @return the list of entities
     */
    List<WorkOutDTO> findAll();

    /**
     * Get the "id" workOut.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WorkOutDTO findOne(Long id);

    /**
     * Delete the "id" workOut.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
