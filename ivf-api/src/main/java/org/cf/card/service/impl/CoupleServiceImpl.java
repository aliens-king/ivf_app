package org.cf.card.service.impl;

import javax.transaction.Transactional;

import org.cf.card.model.Client;
import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;
import org.cf.card.persistence.CoupleDao;
import org.cf.card.service.CoupleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dell on 10/9/2014.
 */
@Service("coupleService")
@Transactional
public class CoupleServiceImpl extends BaseServiceImpl<Couple> implements CoupleService{

    private CoupleDao coupleDao;

    @Autowired
    public void setCoupleDao(CoupleDao coupleDao) {
		this.coupleDao = coupleDao;
		setGenericDao(coupleDao);
	}

//	public Couple createNewCouple(Couple couple) {
//        notNull(couple, "couple can't be null");
//        return coupleDao.save(couple);
//    }

//    public List<Couple> getCouples() {
//        Iterable<Couple> iterable = coupleDao.findAll();
//        Iterator<Couple> iterator = iterable.iterator();
//        List<Couple> codes = new ArrayList<Couple>();
//
//        while (iterator.hasNext()) {
//            codes.add(iterator.next());
//        }
//
//        return codes;
//    }

//    public Couple getCoupleById(long id) {
//        return coupleDao.findById(id);
//    }

    @Override
    public Couple getCoupleByMan(Client man){
        return coupleDao.findByMan(man);
    }

    @Override
    public Couple getCoupleByWoman(Client woman){
        return coupleDao.findByWoman(woman);
    }

    @Override
    public Couple getCoupleByClipart(Clipart clipart){
        return coupleDao.findByClipart(clipart);
    }

//    public Couple updateCouple(Couple couple){
//        notNull(couple, "Couple can't be null");
//        return coupleDao.save(couple);
//    }
//
//    public void deleteCouple(Long id){
//        notNull(id, "Id Couple can't be null");
//        coupleDao.delete(id);
//    }

}
