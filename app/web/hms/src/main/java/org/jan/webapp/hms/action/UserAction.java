/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.action;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jan.webapp.hms.model.page.Json;
import org.jan.webapp.hms.model.page.User;
import org.jan.webapp.hms.service.UserService;


import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Jan.Wang
 *
 */
public class UserAction extends BaseAction implements ModelDriven<User> {
    private static final long serialVersionUID = 1885763494006841107L;

    private static final Logger logger = Logger.getLogger(UserAction.class);

    @Inject
    private UserService userService;
    private User userModel = new User();

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    @Override
    public User getModel() {
        return userModel;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.action.BaseAction#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    public void login() {
        Json json = new Json();
        User user = userService.login(userModel.getUserName(), userModel.getPassword());
        if(null != user){
            json.setMsg("用户登录成功！");
            json.setObj(user);
        }else{
            json.setSuccess(false);
            json.setMsg("用户名或密码错误!");
        }
        responseJson(json);
    }

    public void logout() {
        HttpSession session = getHttpSession();
        if(null != session)
            session.invalidate();
        responseJson(new Json());
    }

}
