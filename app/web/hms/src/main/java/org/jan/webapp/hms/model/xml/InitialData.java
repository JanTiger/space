/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jan.Wang
 *
 */
@XmlRootElement(name = "InitialData")
@XmlAccessorType(XmlAccessType.FIELD)
public class InitialData implements Serializable {
    private static final long serialVersionUID = 173428056853512629L;

    @XmlElement(name = "MenuData")
    private MenuData menuData;

    @XmlElement(name = "UserData")
    private UserData userData;

    /**
     * @return the userData
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * @return the menuData
     */
    public MenuData getMenuData() {
        return menuData;
    }

}
