package com.web.app.repository;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    void deleteById(Long id);

    Optional<AgendaEntity> findById(Long id);

    Set<AgendaEntity> findByUsersid(UsersEntity usersEntity);

    @Modifying
    @Query("UPDATE AgendaEntity a " +
            "SET " +
            "a.updated = ?1, " +
            "a.day = ?2," +
            "a.time = ?3," +
            "a.accessible = ?4," +
            "a.note = ?5 " +
            "WHERE a.id = ?6"
    )
    @Transactional
    void updateAgendaById(@Param("updated") Date updated,
                          @Param("day") DayOfWeek dayOfWeek,
                          @Param("time") String time,
                          @Param("accessible") boolean accessible,
                          @Param("note") String note,
                          @Param("id") Long id);
}