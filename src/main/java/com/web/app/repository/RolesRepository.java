package com.web.app.repository;

import com.web.app.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public interface RolesRepository extends JpaRepository<RolesEntity, String> {

    /**
     * This method finds a {@link RolesEntity} by it's role-name.
     *
     * @param role role-name.
     * @return {@code RolesEntity} instance, having specified role-name.
     */
    RolesEntity findByRole(String role);

    /**
     * This method finds all roles.
     *
     * @return list, containing all {@code RolesEntity} instances.
     */
    List<RolesEntity> findAll();
}
