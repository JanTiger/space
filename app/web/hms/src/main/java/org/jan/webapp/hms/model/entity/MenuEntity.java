/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Jan.Wang
 *
 */
@Entity
@Table(name = "TAB_MENU", schema = "")
public class MenuEntity implements Serializable {
    private static final long serialVersionUID = 2219377195203235455L;

    private String id;
    private String name;
    private String url;
    private String iconUrl;
    private MenuEntity parent;
    private Set<MenuEntity> children = new HashSet<MenuEntity>(0);

    /**
     * @return the id
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
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
    @Column(name = "NAME", nullable = false, length = 30)
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
    @Column(name = "URL", length = 100)
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
    @Column(name = "ICON_URL", length = 100)
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
     * @return the parent
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PID")
    public MenuEntity getParent() {
        return parent;
    }
    /**
     * @param parent the parent to set
     */
    public void setParent(MenuEntity parent) {
        this.parent = parent;
    }
    /**
     * @return the children
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    public Set<MenuEntity> getChildren() {
        return children;
    }
    /**
     * @param children the children to set
     */
    public void setChildren(Set<MenuEntity> children) {
        this.children = children;
    }

}
