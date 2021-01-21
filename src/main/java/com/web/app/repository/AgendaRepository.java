package com.web.app.repository;

import com.web.app.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.Optional;

//todo к спрингдате тоже такая себе документация(лично мне не особо нравится) - слишком поверхностная.
// или все-таки пойдет?

/**
 * A {@link Repository} component, to interact with data in the "agenda" table.
 * <p>
 * <strong>NOTE:</strong> no implementation for this interface needed, a proxy over
 * {@link org.springframework.data.jpa.repository.support.SimpleJpaRepository} will be created,
 * including automatically generation of implementations of methods I wrote in this interface
 * ({@code Spring Data} "understands" what kind of {@code SQL/HQL/JPQL} query is needed by method's name)
 * <p>
 * Also note, that, by default, all methods in this interface and methods in
 * {@code SimpleJpaRepository} will be {@code transactional}, so, no need to annotate them with {@link Transactional}.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    /**
     * This method deletes {@code AgendaEntity} by <strong>it's</strong> id.
     *
     * @param id agenda's id.
     */
    void deleteById(Long id);

    /**
     * This method finds {@code AgendaEntity} by <strong>it's</strong> id.
     *
     * @param id agenda's id.
     * @return an {@link Optional<AgendaEntity>} - a wrapper over {@code AgendaEntity}.
     */
    Optional<AgendaEntity> findById(Long id);

    /**
     * This method updates {@code AgendaEntity} by <strong>it's</strong> id.
     * <p>
     * <strong>NOTE:</strong> it's good practice to annotate methods, annotated with {@link Modifying} annotation
     * with {@link Transactional} as well.
     * See 6.7.1: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions
     *
     * @param updated    when agenda was lastly updated(at the moment I evoked this method)
     * @param dayOfWeek  day
     * @param time       time
     * @param accessible accessible
     * @param note       note
     * @param id         <strong>agenda's id</strong>
     */
    @Modifying
    @Query(
            "UPDATE AgendaEntity a " +
                    "SET " +
                    "a.updated = ?1, " +
                    "a.day = ?2," +
                    "a.time = ?3," +
                    "a.accessible = ?4," +
                    "a.note = ?5 " +
                    "WHERE a.id = ?6"
    )
    @Transactional
    void updateAgendaByItsId(
            @Param("updated") Date updated,
            @Param("day") DayOfWeek dayOfWeek,
            @Param("time") String time,
            @Param("accessible") boolean accessible,
            @Param("note") String note,
            @Param("id") Long id
    );
}