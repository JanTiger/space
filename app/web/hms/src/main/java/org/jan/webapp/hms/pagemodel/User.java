package org.jan.webapp.hms.pagemodel;

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
    
}
