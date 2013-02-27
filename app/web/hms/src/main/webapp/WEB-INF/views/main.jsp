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
<s:include value="head.jsp"></s:include>
</head>
<body class="easyui-layout">
	<s:div data-options="region:'north',href:'navigate!north.action'" cssStyle="height: 60px;overflow: hidden;" cssClass="logo"></s:div>
	<s:div data-options="region:'west',title:'功能导航',href:'navigate!west.action'" cssStyle="width: 200px;overflow: hidden;"></s:div>
	<s:div data-options="region:'center',title:'欢迎使用HMS系统',href:'navigate!center.action'" cssStyle="overflow: hidden;"></s:div>
	<s:div data-options="region:'east',title:'日历',split:true" cssStyle="width: 200px;overflow: hidden;">
		<s:include value="layout/east.jsp"></s:include>
	</s:div>
	<s:div data-options="region:'south',href:'navigate!south.action'" cssStyle="height: 27px;overflow: hidden;"></s:div>
</body>
</html>