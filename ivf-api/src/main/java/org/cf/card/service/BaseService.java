package org.cf.card.service;

import java.util.List;

/**
 * @author Nikhil
 *
 * @param <T>
 */
public interface BaseService<T> {
    /**
     * Find one.
     *
     * @param id
     *            the id
     * @return the t
     */
    T findOne(Long id);

    /**
     * Find all.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Save.
     *
     * @param entity
     *            the entity
     */
    T save(final T entity);

    /**
     * Save or update.
     *
     * @param entity
     *            the entity
     */
    T saveOrUpdate(final T entity);

    /**
     * Update.
     *
     * @param entity
     *            the entity
     */
    T update(final T entity);

    /**
     * Delete.
     *
     * @param entity
     *            the entity
     */
    void delete(final T entity);

    /**
     * Delete by id.
     *
     * @param entityId
     *            the entity id
     */
    void deleteById(final Long entityId);

    /**
     * Delete all.
     */
    void deleteAll();

    /**
     * Save all multiple entities
     *
     * @param entities the entities
     * @return the list
     */
    List<T>  saveAll(List<T> entities);
}
