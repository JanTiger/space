package org.jan.webapp.hms.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jan.webapp.hms.util.Constants;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("struts-default")
@Namespace("/")
public abstract class BaseAction extends ActionSupport {
    private static final long serialVersionUID = 2527281313856828172L;

    private static final Logger logger = Logger.getLogger(BaseAction.class);

    protected final HttpServletRequest getHttpServletRequest(){
        return ServletActionContext.getRequest();
    }

    protected final HttpServletResponse getHttpServletResponse(){
        return ServletActionContext.getResponse();
    }

    protected final HttpSession getHttpSession(){
        return getHttpServletRequest().getSession();
    }

    protected final <T> T getValueFromRequest(String name){
        return (T) getHttpServletRequest().getAttribute(name);
    }

    protected final <T> T getValueFromSession(String name){
        return (T) getHttpSession().getAttribute(name);
    }

    protected final <T> void putValueToRequest(String name, T value){
        getHttpServletRequest().setAttribute(name, value);
    }

    protected final <T> void putValueToSession(String name, T value){
        getHttpSession().setAttribute(name, value);
    }

    protected final void responseError(String msg){
        putValueToRequest(Constants.REQUEST_NAME_ERROR_MSG, msg);
    }

    public void responseJson(Object obj) {
        String json = JSON.toJSONStringWithDateFormat(obj, Constants.FORMAT_DATE_y_M_d_H_m_s);
        HttpServletResponse response = getHttpServletResponse();
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (IOException e) {
            getCurrentLogger().error(e.getMessage());
        }
    }

    private Logger getCurrentLogger(){
        Logger l = getLogger();
        if(null != l)
            return l;
        return logger;
    }

    protected abstract Logger getLogger();
}
