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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Jan.Wang
 *
 */
@Entity
@Table(name = "TAB_ROLE", schema = "")
public class RoleEntity implements Serializable {
    private static final long serialVersionUID = -7435536225440496923L;

    private String id;
    private String name;
    private String permission;
    private String note;
    private int active;
    private Date lastUpdateDt;
    private Set<UserEntity> users = new HashSet<UserEntity>(0);

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
     * @return the permission
     */
    @Column(name = "PERMISSION", length = 200)
    public String getPermission() {
        return permission;
    }
    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }
    /**
     * @return the note
     */
    @Column(name = "NOTE", length = 500)
    public String getNote() {
        return note;
    }
    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * @return the active
     */
    @Column(name = "ACTIVE", length = 1)
    public int getActive() {
        return active;
    }
    /**
     * @param active the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }
    /**
     * @return the lastUpdateDt
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE_DT", length = 7)
    public Date getLastUpdateDt() {
        return lastUpdateDt;
    }
    /**
     * @param lastUpdateDt the lastUpdateDt to set
     */
    public void setLastUpdateDt(Date lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }
    /**
     * @return the users
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<UserEntity> getUsers() {
        return users;
    }
    /**
     * @param users the users to set
     */
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
