/**
 * 
 */
package org.cf.card.persistence;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * The Interface GenericDao: interface for all dao classes
 *
 * @param <T> the generic entity type
 * @param <ID> the generic identifier type
 * @author Nikhil Mahajan
 */
@NoRepositoryBean
public interface GenericDao<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
