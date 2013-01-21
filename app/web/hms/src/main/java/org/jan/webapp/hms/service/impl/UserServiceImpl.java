/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jan.webapp.hms.converter.impl.UserConverter;
import org.jan.webapp.hms.dao.BaseDao;
import org.jan.webapp.hms.model.entity.RoleEntity;
import org.jan.webapp.hms.model.entity.UserEntity;
import org.jan.webapp.hms.model.page.User;
import org.jan.webapp.hms.service.UserService;
import org.jan.webapp.hms.util.Encrypt;
import org.jan.webapp.hms.util.IDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jan.Wang
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    private BaseDao<UserEntity> userDao;
    @Inject
    private BaseDao<RoleEntity> roleDao;
    private UserConverter userConverter = new UserConverter();

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#addUser(org.jan.webapp.hms.model.page.User)
     */
    @Override
    @Transactional
    public User addUser(User user) {
        user.setId(IDGenerator.create());
        Date now = new Date();
        user.setCreateDt(now);
        user.setLastUpdateDt(now);
        UserEntity userEntity = userConverter.convert(user);
        String roleId = user.getRoleId();
        if(null != roleId){
            RoleEntity roleEntity = roleDao.get(RoleEntity.class, roleId);
            userEntity.setRole(roleEntity);
            userDao.save(userEntity);
        }
        return user;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#getUserById(java.lang.String)
     */
    @Override
    public User getUserById(String id) {
        UserEntity userEntity = userDao.get(UserEntity.class, id);
        return userConverter.inverse(userEntity);
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#getUserByRoleId(java.lang.String)
     */
    @Override
    public List<User> getUsersByRoleId(String roleId) {
        List<UserEntity> userEntitys = userDao.find("FROM UserEntity u where u.role.id = ?", new String[]{roleId});
        List<User> users = new ArrayList<User>();
        if(null != userEntitys){
            for(UserEntity userEntity : userEntitys)
                users.add(userConverter.inverse(userEntity));
        }
        return users;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#modifyPassword(org.jan.webapp.hms.model.page.User)
     */
    @Override
    public boolean modifyPassword(User user) {
        UserEntity userEntity = userDao.get(UserEntity.class, user.getId());
        if(null != userEntity){
            userEntity.setPassword(Encrypt.ep(user.getPassword()));
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#modifyUser(org.jan.webapp.hms.model.page.User)
     */
    @Override
    public User modifyUser(User user) {
        user.setLastUpdateDt(new Date());
        UserEntity userEntity = userConverter.convert(user);
        String roleId = user.getRoleId();
        if(null != roleId){
            RoleEntity roleEntity = roleDao.get(RoleEntity.class, roleId);
            if(null != roleEntity){
                userEntity.setRole(roleEntity);
                userDao.update(userEntity);
            }
        }
        return user;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#removeUser(java.lang.String)
     */
    @Override
    public boolean removeUser(String id) {
        UserEntity userEntity = userDao.get(UserEntity.class, id);
        if(null != userEntity){
            userDao.delete(userEntity);
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#getAllUsers()
     */
    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntitys = userDao.find("FROM UserEntity u");
        List<User> users = new ArrayList<User>();
        if(null != userEntitys){
            for(UserEntity userEntity : userEntitys)
                users.add(userConverter.inverse(userEntity));
        }
        return users;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#getUsersByCondition(org.jan.webapp.hms.model.page.User)
     */
    @Override
    public List<User> getUsersByCondition(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#login(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=true)
    public User login(String userName, String password) {
        UserEntity userEntity = userDao.get("FROM UserEntity u where u.userName = ? and u.password = ?", new String[]{userName, Encrypt.ep(password)});
        if(null != userEntity)
            return userConverter.inverse(userEntity);
        return null;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.service.UserService#getUserByUserName(java.lang.String)
     */
    @Override
    @Transactional(readOnly=true)
    public User getUserByUserName(String userName) {
        UserEntity userEntity = userDao.get("FROM UserEntity u where u.userName = ?", new String[]{userName});
        if(null != userEntity)
            return userConverter.inverse(userEntity);
        return null;
    }

}
