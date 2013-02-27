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

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.jan.webapp.hms.model.page.Online;
import org.jan.webapp.hms.service.UserService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Jan.Wang
 *
 */
@Action(value = "onlineAction")
public class OnlineAction extends BaseAction implements ModelDriven<Online> {
    private static final long serialVersionUID = 4226279206300480165L;

    private static final Logger logger = Logger.getLogger(OnlineAction.class);

    @Inject
    private UserService userService;
    private Online onlineModel = new Online();

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.action.BaseAction#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    @Override
    public Online getModel() {
        return onlineModel;
    }

    public void viewOnlines(){
        this.responseJson(userService.getOnlines(onlineModel));
    }

}
