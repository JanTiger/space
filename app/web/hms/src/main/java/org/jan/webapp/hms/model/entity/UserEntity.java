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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Jan.Wang
 *
 */
@Entity
@Table(name = "TAB_USER", schema = "")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 436350781326471647L;

    private String id;
    private String userName;
    private String password;
    private String realName;
    private String gender;
    private String note;
    private int active;
    private Date createDt;
    private Date lastUpdateDt;
    private RoleEntity role;
    /**
     * @return the role
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public RoleEntity getRole() {
        return role;
    }
    /**
     * @param role the role to set
     */
    public void setRole(RoleEntity role) {
        this.role = role;
    }
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
     * @return the userName
     */
    @Column(name = "USER_NAME", unique = true, nullable = false, length = 30)
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the password
     */
    @Column(name = "PASSWORD", nullable = false, length = 30)
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the realName
     */
    @Column(name = "REAL_NAME", length = 20)
    public String getRealName() {
        return realName;
    }
    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }
    /**
     * @return the gender
     */
    @Column(name = "GENDER", length = 2)
    public String getGender() {
        return gender;
    }
    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
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
     * @return the createDt
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DT", length = 7)
    public Date getCreateDt() {
        return createDt;
    }
    /**
     * @param createDt the createDt to set
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
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

}
