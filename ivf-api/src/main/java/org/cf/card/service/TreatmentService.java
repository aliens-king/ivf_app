/**
 *
 */
package org.cf.card.service;

import java.util.Date;
import java.util.List;

import org.cf.card.dto.TreatmentDto;
import org.cf.card.model.Treatment;

/**
 * The Interface TreatmentService.
 *
 * @author Nikhil Mahajan
 */
public interface TreatmentService extends BaseService<Treatment> {

    /**
     * Gets the by name.
     *
     * @param name the name
     * @return the by name
     */
    public List<Treatment> getByName(String name) ;
/**
 * Gets the by start date.
 *
 * @param startDate the start date
 * @return the by start date
 */
    public List<Treatment> getByStartDate(Date startDate) ;

/**
 * Gets the by end date.
 *
 * @param endDate the end date
 * @return the by end date
 */
    public List<Treatment> getByEndDate(Date endDate);
//
//    public Treatment getByCodes(Codes code);
//
//    public List<Treatment> getTreatmentssByDate(Date date) ;
    public Treatment getLatestTreatment();

 //   public Treatment updateTreatment(TreatmentDto treatmentDto);

    /* get latest treatment by passing parameter coupleId*/
    public TreatmentDto findLatesTreatmentByCoupleId(Long coupleId) ;
    
    
    public Treatment updateTreatmentEntity(TreatmentDto treatmentDto);
}
