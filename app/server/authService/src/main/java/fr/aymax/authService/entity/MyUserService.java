package fr.aymax.authService.entity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import fr.aymax.authService.dao.UserDAO;
import fr.aymax.authService.entity.MyUser;

@Service //So we can inject this class to other spring managed beans
public class MyUserService {

	@Autowired
	private UserDAO userDAO;

	public MyUser lookup(String username) {
		return userDAO.findByUsername(username);
	}

	public void save(MyUser user) {
		userDAO.save(user);
	}

	public boolean usernameExists(String username) {
		if (userDAO.findByUsername(username) != null)
			return true;
		return false;
	}
}
