/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.jan.webapp.hms.model.page.Menu;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Jan.Wang
 *
 */
@Action(value = "menuAction", results = {})
public class MenuAction extends BaseAction implements ModelDriven<Menu> {
    private static final long serialVersionUID = 4921255952867778648L;

    private static final Logger logger = Logger.getLogger(MenuAction.class);

    private Menu menuModel = new Menu();

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
    public Menu getModel() {
        return menuModel;
    }

    public void showAllTreeNode(){
        //TODO
    }

}
