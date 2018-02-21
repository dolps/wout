package com.wout.dolp.service;

import com.wout.dolp.service.dto.SettDTO;
import java.util.List;

/**
 * Service Interface for managing Sett.
 */
public interface SettService {

    /**
     * Save a sett.
     *
     * @param settDTO the entity to save
     * @return the persisted entity
     */
    SettDTO save(SettDTO settDTO);

    /**
     * Get all the setts.
     *
     * @return the list of entities
     */
    List<SettDTO> findAll();

    /**
     * Get the "id" sett.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SettDTO findOne(Long id);

    /**
     * Delete the "id" sett.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
