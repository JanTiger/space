package org.jan.webapp.hms.model.page;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = -6135681563835767389L;

	private String id;
    private String userName;
    private String password;
    private String realName;
    private String gender;
    private String note;
    private int active;
    private Date createDt;
    private Date lastUpdateDt;
    private String roleId;

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
     * @return the userName
     */
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
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }
    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
