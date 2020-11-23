package com.web.app.repository;

import com.web.app.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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