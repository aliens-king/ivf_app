package org.cf.card.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.cf.card.model.Clipart;
import org.cf.card.model.Couple;
import org.cf.card.persistence.ClipartDao;
import org.cf.card.service.ClipartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dell on 10/7/2014.
 */


@Transactional
@Service("clipartService")
public class ClipartServiceImpl extends BaseServiceImpl<Clipart> implements ClipartService{


    private ClipartDao clipartDao;


    @Autowired
    public void setClipartDao(ClipartDao clipartDao) {
		this.clipartDao = clipartDao;
		setGenericDao(clipartDao);
	}

//	public Clipart createNewClipart(Clipart clipart) {
//        notNull(clipart, "clipart can't be null");
//        return clipartDao.save(clipart);
//    }

//    public List<Clipart> getCliparts() {
//        Iterable<Clipart> iterable = clipartDao.findAll();
//        Iterator<Clipart> iterator = iterable.iterator();
//        List<Clipart> clipart = new ArrayList<>();
//        while (iterator.hasNext()) {
//            clipart.add(iterator.next());
//        }
//        return clipart;
//    }

    @Override
    public List<Clipart> getClipartBySource(String source) {
        return clipartDao.findBySource(source);
    }

//    public Clipart getClipartById(long id) {
//        return clipartDao.findById(id);
//    }

    @Override
    public Clipart getClipartByCouple(Couple couple){return clipartDao.findByCouple(couple);}

    @Override
    public byte[] getClipartImage(String path){
//        try {
//            return ImageIO.read(new File(clipartDao.findById(id).getSource()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
        	File f = new File(path);
        	byte[] imageBytes = null;
            if(f.exists()){
            	// Prepare buffered image.
                BufferedImage img = ImageIO.read(f);

                // Create a byte array output stream.
                ByteArrayOutputStream bao = new ByteArrayOutputStream();

                // Write to output stream
                ImageIO.write(img, "jpg", bao);

                imageBytes = bao.toByteArray();
            }

            return imageBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public Clipart updateClipart(Clipart clipart){
//        notNull(clipart, "client can't be null");
//        return clipartDao.save(clipart);
//    }
//
//    public void deleteClipart(long id){
//        notNull(id, "id can't be null");
//        clipartDao.delete(id);
//    }


}
