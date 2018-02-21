package com.wout.dolp.repository;

import com.wout.dolp.domain.WorkOut;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkOutRepository extends JpaRepository<WorkOut, Long> {

}
