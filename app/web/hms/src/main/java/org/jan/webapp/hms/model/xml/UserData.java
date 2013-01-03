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
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Jan.Wang
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UserData implements Serializable {
    private static final long serialVersionUID = 147167540907076666L;

    @XmlAttribute(name = "userName", required = true)
    private String userName;
    @XmlAttribute(name = "password", required = true)
    private String password;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
