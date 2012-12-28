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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author Jan.Wang
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuData implements Serializable {
    private static final long serialVersionUID = -4288962390514021010L;

    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "url")
    private String url;
    @XmlAttribute(name = "iconUrl")
    private String iconUrl;
    @XmlAttribute(name = "seq")
    private int seq;
    @XmlElementWrapper(name = "MenuList")
    @XmlElement(name = "MenuData")
    private List<MenuData> menuList;

}
