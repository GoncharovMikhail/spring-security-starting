package com.web.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * An abstract class, containing fields "created" and "updated".
 * <p>
 * Any class, inherited from {@code BaseEntity} will also have these fields.
 * <p>
 * The {@link MappedSuperclass} annotation indicates that this entity class
 * has no corresponding table in the database.
 * <p>
 * I use {@link Getter} and {@link Setter} in most entity classes.
 *
 * @see AgendaEntity
 * @see RolesEntity
 * @see UsersEntity
 */
@MappedSuperclass
@Getter
@Setter
abstract class BaseEntity {

    /**
     * {@link CreatedDate} annotation indicates that a field represent a date the entity was created.
     * {@link Column} annotation indicates that a field is a column in a database.
     * Also, {@code Column} annotation contains some settings:
     * <ul>
     *     <li>
     *         {@code name} indicates the name of column in the database,
     *     </li>
     *     <li>
     *         {@code updatable = false} indicates that this field can't be updated,
     *     </li>
     *     <li>
     *         {@code nullable = false} indicates that this field (in database) is never {@code null}.
     *     </li>
     * </ul>
     */
    @CreatedDate
    @Column(name = "created", updatable = false, nullable = false)
    private Date created;

    /**
     * {@link LastModifiedDate} indicates that a field represents a date the entity was last modified.
     * {@link Column} ...
     */
    @LastModifiedDate
    @Column(name = "updated", nullable = false)
    private Date updated;
}
