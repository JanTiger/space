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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jan.Wang
 *
 */
@Entity
@Table(name = "TAB_BEHAVIOR", schema = "")
public class Behavior implements Serializable {
    private static final long serialVersionUID = 1593468690301952050L;

    private String id;
    private String behaviorNo;
    private String name;
    private String note;
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
     * @return the behaviorNo
     */
    @Column(name = "BEHAVIOR_NO", unique = true, nullable = false, length = 20)
    public String getBehaviorNo() {
        return behaviorNo;
    }
    /**
     * @param behaviorNo the behaviorNo to set
     */
    public void setBehaviorNo(String behaviorNo) {
        this.behaviorNo = behaviorNo;
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

}
