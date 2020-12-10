package com.web.app.repository;

import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * A {@link Repository} component, to interact with data in the "agenda" table.
 * <p>
 * <strong>NOTE:</strong> no implementation for this interface needed, a proxy over
 * {@link org.springframework.data.jpa.repository.support.SimpleJpaRepository} will be created,
 * including automatically generation of implementations of methods we wrote in this interface
 * (Spring Data understand what kind of HQL query is needed by method's name)
 * <p>
 * Also note, that, by default, all methods in this interface and methods in
 * {@code SimpleJpaRepository} will be transactional, so, no need to annotate them with {@link Transactional}.
 */
@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findById(Integer id);

    UsersEntity findByUsername(String username);

    UsersEntity findByEmailAndUsername(String email, String name);

    @Modifying
    @Query("UPDATE UsersEntity u SET enabled = ?1 WHERE u.username = ?2")
    @Transactional
    void enableOrDisableUser(@Param("enabled") boolean toEnable, @Param("username") String username);
}