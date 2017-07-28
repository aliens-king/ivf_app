package org.cf.card.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.persistence.GenericDao;
import org.springframework.data.jpa.repository.Modifying;

/**
 * The Class BaseService.
 *
 * @param <T> the generic type
 * @author Nikhil Mahajan
 */
public abstract class BaseServiceImpl<T> {

    private GenericDao<T, Long> genericDao;

    /**
     * Gets the generic dao.
     *
     * @return genericDao
     */
    public GenericDao<T, Long> getGenericDao() {
        return genericDao;
    }

    /**
     * Sets the generic dao.
     *
     * @param genericDao GenericDao
     */
    public void setGenericDao(
            final GenericDao<T, Long> genericDao) {
        this.genericDao = genericDao;
    }

    /**
     * Find one.
     *
     * @param id
     *            the id
     * @return the t
     */

    public T findOne(final Long id) {
    	notNull(id, " id to be fetched can't be null");
        return genericDao.findOne(id);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List<T> findAll() {
        return genericDao.findAll();
    }

    /**
     * Save.
     *
     * @param entity
     *            the entity
     */
    @Transactional
    public T save(final T entity) {
    	notNull(entity, " can't to be saved cannot be null");
        return genericDao.save(entity);
    }

    /**
     * Update.
     *
     * @param entity
     *            the entity
     */
    @Transactional
    public T update(final T entity) {
    	notNull(entity, " entity to be updated can't be null");
       return genericDao.save(entity);

    }

    /**
     * Save or update.
     *
     * @param entity
     *            the entity
     */
    @Transactional
    public T saveOrUpdate(final T entity) {
    	notNull(entity, " entity to be saved can't be null");
        return genericDao.save(entity);
    }

    /**
     * Delete.
     *
     * @param entity
     *            the entity
     */
    @Transactional
    public void delete(final T entity) {
    	notNull(entity, " entity to be deleted can't be null");
        genericDao.delete(entity);
    }

    /**
     * Delete by id.
     *
     * @param entityId
     *            the entity id
     */
    @Modifying
    @Transactional
    public void deleteById(final Long entityId) {
        notNull(entityId, "id to be deleted can't be null");
        genericDao.delete(entityId);
    }

    @Modifying
    @Transactional
    public void  deleteAll(){
    	genericDao.deleteAll();
    }

    @Transactional
    public List<T>  saveAll(List<T> entities){
    	return genericDao.save(entities);
    }

}
