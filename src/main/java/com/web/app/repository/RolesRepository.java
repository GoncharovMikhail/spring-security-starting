package com.web.app.repository;

import com.web.app.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//todo к спрингдате тоже такая себе документация(лично мне не особо нравится) - слишком поверхностная.
// или все-таки пойдет?

/**
 * A {@link Repository} component, to interact with data in the "roles" table.
 * <p>
 * <strong>NOTE:</strong> no implementation for this interface needed, a proxy over
 * {@link org.springframework.data.jpa.repository.support.SimpleJpaRepository} will be created,
 * including automatically generation of implementations of methods we wrote in this interface
 * ({@code Spring Data} "understands" what kind of {@code SQL/HQL/JPQL} query is needed by method's name)
 * <p>
 * Also note, that, by default, all methods in this interface and methods in
 * {@code SimpleJpaRepository} will be {@code transactional}, so, no need to annotate them with {@link Transactional}.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
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
     * This method finds <strong>all</strong> {@code RolesEntities}.
     *
     * @return list, containing all {@code RolesEntities}.
     */
    List<RolesEntity> findAll();
}
