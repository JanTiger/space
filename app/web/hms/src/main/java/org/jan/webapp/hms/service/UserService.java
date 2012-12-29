/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.service;

import java.util.List;

import org.jan.webapp.hms.model.page.User;

/**
 * @author Jan.Wang
 *
 */
public interface UserService extends BaseService {

    User addUser(User user);

    User modifyUser(User user);

    boolean modifyPassword(User user);

    boolean removeUser(String id);

    User getUserById(String id);

    List<User> getUsersByRoleId(String roleId);

    List<User> getAllUsers();

    List<User> getUsersByCondition(User user);

    User login(String userName, String password);

    User getUserByUserName(String userName);

}
