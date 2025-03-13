package com.feedhanjum.team.repository;

import com.feedhanjum.team.domain.Team;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Team t where t.id = :id")
    Optional<Team> findByIdForUpdateExclusiveLock(@Param("id") Long id);


    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select t from Team t where t.id = :id")
    Optional<Team> findByIdForUpdateSharedLock(@Param("id") Long id);

}
