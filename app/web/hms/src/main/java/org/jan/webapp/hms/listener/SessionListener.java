/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jan.webapp.hms.model.page.SessionInfo;
import org.jan.webapp.hms.service.UserService;
import org.jan.webapp.hms.util.Constants;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Jan.Wang
 *
 */
public class SessionListener implements ServletContextListener, HttpSessionListener {

    private UserService userService;

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        userService = (UserService) WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(UserService.class);

    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        if(null != session){
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Constants.SESSION_NAME_SESSION);
            if(null != sessionInfo)
                userService.removeOnline(sessionInfo.getLoginName());
        }
    }

}
