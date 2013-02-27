package org.jan.webapp.hms.service;

import java.util.List;

import org.jan.webapp.hms.model.page.DataGrid;
import org.jan.webapp.hms.model.page.Online;
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

    DataGrid<Online> getOnlines(Online online);

    void addOnline(Online online);

    void removeOnline(String loginName);

}
