/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.model.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jan.webapp.hms.model.xml.MenuData;

/**
 * @author Jan.Wang
 *
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = 6696629857511032497L;

    private String id;
    private String name;
    private String url;
    private String iconUrl;
    private int seq;

    private String pid;
    private String text;
    private Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Menu(){}

    public Menu(String pid, MenuData data){
        if(null != data){
            id = data.getId();
            name = data.getName();
            text = data.getName();
            url = data.getUrl();
            iconUrl = data.getIconUrl();
            seq = data.getSeq();
            this.pid = pid;
            attributes.put("url", url);
        }
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the iconUrl
     */
    public String getIconUrl() {
        return iconUrl;
    }
    /**
     * @param iconUrl the iconUrl to set
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    /**
     * @return the seq
     */
    public int getSeq() {
        return seq;
    }
    /**
     * @param seq the seq to set
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }
    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }
    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

}
