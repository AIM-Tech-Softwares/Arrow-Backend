package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    Page<State> findAllByIsActive(Pageable pageable, boolean isActive);
}
