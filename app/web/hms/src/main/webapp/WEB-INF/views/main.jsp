<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>HMS</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="hms" />
<meta http-equiv="description" content="hms" />
<jsp:include page="head.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<s:div data-options="region:'north',href:'/WEB-INF/views/layout/north.jsp'" cssStyle="height: 60px;overflow: hidden;" cssClass="logo"></s:div>
	<s:div data-options="region:'west',title:'功能导航',href:'/WEB-INF/views/layout/west.jsp'" cssStyle="width: 200px;overflow: hidden;"></s:div>
	<s:div data-options="region:'center',title:'欢迎使用SyPro示例系统',href:2'/WEB-INF/views/layout/center.jsp'" cssStyle="overflow: hidden;"></s:div>
	<s:div data-options="region:'east',title:'日历',split:true" cssStyle="width: 200px;overflow: hidden;">
		<s:include value="/WEB-INF/views/layout/east.jsp"></s:include>
	</s:div>
	<s:div data-options="region:'south',href:'/WEB-INF/views/layout/south.jsp'" cssStyle="height: 27px;overflow: hidden;"></s:div>
</body>
</html>