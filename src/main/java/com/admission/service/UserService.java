package com.admission.service;

import com.admission.entity.User;

public interface UserService {
	public User findUser(int userId) throws Exception;
	
	public User passwordUser(int userId, String originPwd, String newPwd) throws Exception;
	
	public User login(String username, String password) throws Exception;
}
