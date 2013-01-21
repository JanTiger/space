package org.jan.webapp.hms.model.page;

import java.io.Serializable;

/**
 * @author Jan.Wang
 *
 */
public class SessionInfo implements Serializable {
    private static final long serialVersionUID = -9125191306247361462L;

    private String userId;
    private String loginName;
    private String loginPassword;
    private String ip;
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }
    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    /**
     * @return the loginPassword
     */
    public String getLoginPassword() {
        return loginPassword;
    }
    /**
     * @param loginPassword the loginPassword to set
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }
    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

}
