package org.jan.webapp.hms.model.page;

import java.io.Serializable;

/**
 * @author Jan.Wang
 *
 */
public class Online implements Serializable {
    private static final long serialVersionUID = -5417730772188456826L;

    private String ip;
    private String loginName;
    private String loginTime;

    public Online(){}

    public Online(String loginName, String loginTime, String ip){
        this.loginName = loginName;
        this.loginTime = loginTime;
        this.ip = ip;
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
     * @return the loginTime
     */
    public String getLoginTime() {
        return loginTime;
    }
    /**
     * @param loginTime the loginTime to set
     */
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    //for model
    private int page;
    private int rows;
    private String sort;
    private String order;
    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }
    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }
    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }
    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }
    /**
     * @return the sort
     */
    public String getSort() {
        return sort;
    }
    /**
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }
    /**
     * @return the order
     */
    public String getOrder() {
        return order;
    }
    /**
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

}
