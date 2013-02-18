package org.jan.webapp.hms.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * @author Jan.Wang
 *
 */
@Action(value = "dataSourceAction", results = { @Result(name = "druid", location = "/druid/index.html", type = "redirect") })
public class DataSourceAction extends BaseAction {
    private static final long serialVersionUID = -2736117951798392042L;

    private static final Logger logger = Logger.getLogger(DataSourceAction.class);

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.action.BaseAction#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    public String druid() {
        return "druid";
    }
}
