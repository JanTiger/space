/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.cache;

import java.util.ArrayList;
import java.util.List;

import org.jan.webapp.hms.model.page.Menu;

/**
 * @author Jan.Wang
 *
 */
public class MenuCache {

    private List<Menu> menus = new ArrayList<Menu>();

    private static MenuCache cache = new MenuCache();

    private MenuCache(){}

    public static MenuCache getInstance(){
        return cache;
    }

    public List<Menu> getMenuList(){
        return menus;
    }

    public void addMenu(Menu menu){
        menus.add(menu);
    }

}
