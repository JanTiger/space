/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Jan.Wang
 *
 */
@Entity
@Table(name = "TAB_MODULE_BEHAVIOR", schema = "")
public class ModuleBehaviorEntity implements Serializable {
    private static final long serialVersionUID = 6295456283490764363L;

    private String id;
    private ModuleEntity module;
    private BehaviorEntity behavior;
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
     * @return the module
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODULE_NO", nullable = false)
    public ModuleEntity getModule() {
        return module;
    }
    /**
     * @param module the module to set
     */
    public void setModule(ModuleEntity module) {
        this.module = module;
    }
    /**
     * @return the behavior
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BEHAVIOR_NO", nullable = false)
    public BehaviorEntity getBehavior() {
        return behavior;
    }
    /**
     * @param behavior the behavior to set
     */
    public void setBehavior(BehaviorEntity behavior) {
        this.behavior = behavior;
    }

}
