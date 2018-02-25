package com.wout.dolp.repository;

import com.wout.dolp.domain.Program;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Program entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query("select program from Program program where program.user.login = ?#{principal.username}")
    List<Program> findByUserIsCurrentUser();
}
