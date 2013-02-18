package org.jan.webapp.hms.action;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.jan.webapp.hms.model.page.Json;
import org.jan.webapp.hms.model.page.SessionInfo;
import org.jan.webapp.hms.model.page.User;
import org.jan.webapp.hms.service.UserService;
import org.jan.webapp.hms.util.Constants;
import org.jan.webapp.hms.util.IpUtil;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Jan.Wang
 *
 */
@Action(value = "userAction", results = {@Result(name="login", location="/WEB-INF/views/main.jsp"), @Result(name="logout", location="/index.jsp")})
public class UserAction extends BaseAction implements ModelDriven<User> {
    private static final long serialVersionUID = 1885763494006841107L;

    private static final Logger logger = Logger.getLogger(UserAction.class);

    @Inject
    private UserService userService;
    private User userModel = new User();

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    @Override
    public User getModel() {
        return userModel;
    }

    /* (non-Javadoc)
     * @see org.jan.webapp.hms.action.BaseAction#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    public String login() {
        Json json = new Json();
        String location = "logout";
        User user = userService.login(userModel.getUserName(), userModel.getPassword());
        if(null != user){
            json.setMsg("用户登录成功！");
            json.setObj(user);
            saveSessionInfo(user);
            location = "login";
        }else{
            json.setSuccess(false);
            json.setMsg(this.getText(Constants.MSG_KEY_LOGIN_FAILED));
            this.addActionError(this.getText(Constants.MSG_KEY_LOGIN_FAILED));
        }
        return location;
    }

    public String logout() {
        HttpSession session = getHttpSession();
        if(null != session)
            session.invalidate();
        return "logout";
    }

    public void showUserInfo(){
        //TODO
    }

    public void modifyCurrentUserPwd(){
        //TODO
    }

    public void datagrid(){
        //TODO
    }

    private void saveSessionInfo(User user){
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUserId(user.getId());
        sessionInfo.setLoginName(user.getUserName());
        sessionInfo.setLoginPassword(user.getPassword());
        sessionInfo.setIp(IpUtil.getIpAddr(getHttpServletRequest()));
        putValueToSession(Constants.SESSION_NAME_SESSION, sessionInfo);
    }

}
