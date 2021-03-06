/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.listener;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jan.common.utils.io.IOUtils;
import org.jan.common.utils.xml.JAXBXmlUtils;
import org.jan.webapp.hms.cache.MenuCache;
import org.jan.webapp.hms.model.page.Menu;
import org.jan.webapp.hms.model.page.User;
import org.jan.webapp.hms.model.xml.InitialData;
import org.jan.webapp.hms.model.xml.MenuData;
import org.jan.webapp.hms.model.xml.UserData;
import org.jan.webapp.hms.service.UserService;
import org.jan.webapp.hms.util.Constants;
import org.jan.webapp.hms.util.Encrypt;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Jan.Wang
 *
 */
public class DeployListener implements ServletContextListener {

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
        InitialData data = getInitialData();
        if(null != data){
            userService = (UserService) WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(UserService.class);
            repairUser(data.getUserData());
            initMenu("-1", data.getMenuData());
        }
    }

    private void initMenu(String pid, MenuData menuData){
        if(null != menuData){
            MenuCache cache = MenuCache.getInstance();
            cache.addMenu(new Menu(pid, menuData));
            List<MenuData> datas = menuData.getMenuList();
            if(null != datas){
                for(MenuData data : datas){
                    initMenu(menuData.getId(), data);
                }
            }

        }
    }

    private void repairUser(UserData userData){
        if(null != userData && null != userData.getUserName()){
            User user = userService.getUserByUserName(Encrypt.dp(userData.getUserName()));
            if(null == user){
                user = new User();
                user.setRoleId("-1");
                user.setUserName(Encrypt.dp(userData.getUserName()));
                user.setPassword(Encrypt.dp(userData.getPassword()));
                userService.addUser(user);
            }
        }
    }

    private InitialData getInitialData(){
        InitialData data = null;
        InputStream is = DeployListener.class.getClassLoader().getResourceAsStream(Constants.INITIALDATA_PATH);
        data = JAXBXmlUtils.xmlToObj(is, InitialData.class);
        IOUtils.closeQuietly(is);
        return data;
    }

}
