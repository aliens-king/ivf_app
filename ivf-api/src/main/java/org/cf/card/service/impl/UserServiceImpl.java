package org.cf.card.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.cf.card.model.User;
import org.cf.card.persistence.UserDao;
import org.cf.card.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dell on 10/10/2014.
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		setGenericDao(userDao);
	}

	// public User createNewLogin(User login){
	// notNull(login, "login can't be null");
	// return userDao.save(login);
	// }
	//
	// public List<User> getLogins(){
	// Iterable<User> iterable = userDao.findAll();
	// Iterator<User> iterator = iterable.iterator();
	// List<User> login = new ArrayList<>();
	//
	// while (iterator.hasNext()) {
	// login.add(iterator.next());
	// }
	//
	// return login;
	// }
	//
	// public User getById(long id){
	// return userDao.findById(id);
	// }

	@Override
	public List<User> getByLoginCode(String code) {
		// return userDao.findByLoginCode(code);
		return null;
	}

	// public User updateLogin(User login){
	// notNull(login, "login can't be null");
	// return userDao.save(login);
	// }
	//
	// public void deleteLogin(long loginId){
	// notNull(loginId, "login can't be null");
	// userDao.delete(loginId);
	// }

	@Override
	public User findByPassword(String password) {
		User user = userDao.findByLoginCode(password);
		if (null != user) {
			String loginCodeFromDB = user.getLoginCode();
			if (loginCodeFromDB.equals(password)) {
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}
}
