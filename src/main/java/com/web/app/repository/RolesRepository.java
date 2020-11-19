package com.web.app.repository;

import com.web.app.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, String> {

    RolesEntity findByRole(String role);

    List<RolesEntity> findAll();
}
