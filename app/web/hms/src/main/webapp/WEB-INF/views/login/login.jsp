<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><%@ taglib uri="/struts-tags" prefix="s" %><%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%><head>    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>    <title>Login - hms</title>    <link href="resources/css/login.css" rel="stylesheet" type="text/css"/>    <script type="text/javascript" src="resources/script/jquery-easyui-1.3.1/jquery-1.8.0.min.js" charset="utf-8"></script>    <script type="text/javascript">	    function loginListen(event) {	        if (event.keyCode == 13) {	        	login();	        }	    }	    function login(){		    if(isEmpty($("#userName")) || isEmpty($("#password")))			    return null;		    $("#loginForm").submit();	    }	    function isEmpty(obj){		    if(obj.val() == ''){			    obj.focus();			    return true;		    }		    return false;		}    </script></head><body class="bg_class" onkeydown="loginListen(event);"><s:div cssClass="wrapper">	<s:div cssClass="content_class">        <s:div cssClass="logo_class"></s:div>        <s:div cssClass="title_class"></s:div>        <s:div id="errorBox">        	<s:actionerror labelposition="left"/>        </s:div>        <s:form id="loginForm" action="userAction!login.action" method="post" theme="simple">            <hr />            <fieldset>            <s:div cssClass="field-group">                <s:textfield name="userName" cssClass="text" id="userName" key="login.userName" theme="xhtml"/>            </s:div>            <s:div cssClass="field-group">                <s:password name="password" cssClass="text" id="password" key="login.password"  theme="xhtml"/>            </s:div>            </fieldset>            <hr />            <s:div id="btnDiv">                <s:a href="#" id="login_button" onclick="login();" cssClass="login_button origin-ux-button origin-ux-button-tertiary">                   <span><s:text name="login.submit"></s:text></span>                </s:a>            </s:div>        </s:form>    </s:div></s:div></body></html>