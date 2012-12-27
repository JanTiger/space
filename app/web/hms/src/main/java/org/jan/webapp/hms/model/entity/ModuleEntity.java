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
@Table(name = "TAB_MODULE", schema = "")
public class ModuleEntity implements Serializable {
    private static final long serialVersionUID = 8409255999115853976L;

    private String id;
    private String moduleNo;
    private String name;
    private String note;
    private ModuleEntity parent;
    private Set<ModuleEntity> children = new HashSet<ModuleEntity>(0);
    private Set<ModuleBehaviorEntity> moduleBehaviors = new HashSet<ModuleBehaviorEntity>(0);

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
     * @return the moduleNo
     */
    @Column(name = "MODULE_NO", unique = true, nullable = false, length = 20)
    public String getModuleNo() {
        return moduleNo;
    }
    /**
     * @param moduleNo the moduleNo to set
     */
    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }
    /**
     * @return the name
     */
    @Column(name = "NAME", nullable = false, length = 50)
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
     * @return the parent
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PID")
    public ModuleEntity getParent() {
        return parent;
    }
    /**
     * @param parent the parent to set
     */
    public void setParent(ModuleEntity parent) {
        this.parent = parent;
    }
    /**
     * @return the children
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    public Set<ModuleEntity> getChildren() {
        return children;
    }
    /**
     * @param children the children to set
     */
    public void setChildren(Set<ModuleEntity> children) {
        this.children = children;
    }
    /**
     * @return the moduleBehaviors
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "module")
    public Set<ModuleBehaviorEntity> getModuleBehaviors() {
        return moduleBehaviors;
    }
    /**
     * @param moduleBehaviors the moduleBehaviors to set
     */
    public void setModuleBehaviors(Set<ModuleBehaviorEntity> moduleBehaviors) {
        this.moduleBehaviors = moduleBehaviors;
    }
}
