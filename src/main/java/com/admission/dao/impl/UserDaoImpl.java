package com.admission.dao.impl;

import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.UserDao;
import com.admission.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoHibernate<User> implements UserDao {

}
