/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.converter.impl;

import org.jan.webapp.hms.converter.Converter;
import org.jan.webapp.hms.model.entity.UserEntity;
import org.jan.webapp.hms.model.page.User;
import org.jan.webapp.hms.util.Encrypt;

/**
 * @author Jan.Wang
 *
 */
public class UserConverter implements Converter<User, UserEntity> {

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public UserEntity convert(User user) {
        if(null == user)
            return null;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(user.getUserName());
        userEntity.setGender(user.getGender());
        userEntity.setRealName(user.getRealName());
        userEntity.setCreateDt(user.getCreateDt());
        userEntity.setId(user.getId());
        userEntity.setActive(user.getActive());
        userEntity.setNote(user.getNote());
        userEntity.setPassword(Encrypt.ep(user.getPassword()));
        userEntity.setLastUpdateDt(user.getLastUpdateDt());
        //TODO role
        return userEntity;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.converter.Converter#inverse(java.lang.Object)
     */
    @Override
    public User inverse(UserEntity userEntity) {
        if(null == userEntity)
            return null;
        User user = new User();
        user.setUserName(userEntity.getUserName());
        user.setGender(userEntity.getGender());
        user.setRealName(userEntity.getRealName());
        user.setCreateDt(userEntity.getCreateDt());
        user.setId(userEntity.getId());
        user.setActive(userEntity.getActive());
        user.setNote(userEntity.getNote());
        user.setPassword(Encrypt.dp(userEntity.getPassword()));
        user.setLastUpdateDt(userEntity.getLastUpdateDt());
        user.setRoleId(userEntity.getRole().getId());
        return user;
    }

}
