package org.jan.webapp.hms.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jan.webapp.hms.annotation.Authority;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("struts-default")
@Namespace("/")
public class BaseAction extends ActionSupport {

    @Authority(module = "", behavior = "")
    public void myaction(){

    }
}
