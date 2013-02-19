package org.jan.webapp.hms.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * @author Jan.Wang
 *
 */
@Action(value = "navigate", results = {@Result(name="north", location="/WEB-INF/views/layout/north.jsp")
        , @Result(name="west", location="/WEB-INF/views/layout/west.jsp")
        , @Result(name="south", location="/WEB-INF/views/layout/south.jsp")
        , @Result(name="center", location="/WEB-INF/views/layout/center.jsp")
        , @Result(name="portal", location="/WEB-INF/views/layout/portal.jsp")
        , @Result(name="portal_about", location="/WEB-INF/views/layout/portal/about.jsp")
        , @Result(name="portal_link", location="/WEB-INF/views/layout/portal/link.jsp")
        , @Result(name="portal_repair", location="/WEB-INF/views/layout/portal/repair.jsp")
        , @Result(name="portal_about2", location="/WEB-INF/views/layout/portal/about2.jsp")
        , @Result(name="portal_qun", location="/WEB-INF/views/layout/portal/qun.jsp")})
public class NaviAction extends BaseAction {
    private static final long serialVersionUID = -1614715367657485379L;

    private static final Logger logger = Logger.getLogger(NaviAction.class);

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.action.BaseAction#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    public String north(){
        return "north";
    }

    public String west(){
        return "west";
    }

    public String south(){
        return "south";
    }

    public String center(){
        return "center";
    }

    public String portal(){
        return "portal";
    }

    public String portal_about(){
        return "portal_about";
    }

    public String portal_link(){
        return "portal_link";
    }

    public String portal_repair(){
        return "portal_repair";
    }

    public String portal_about2(){
        return "portal_about2";
    }

    public String portal_qun(){
        return "portal_qun";
    }

}
