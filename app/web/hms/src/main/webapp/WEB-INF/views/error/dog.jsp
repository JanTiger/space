<%@ page language="java" pageEncoding="UTF-8"%>
<%
	double d = Math.random() * 10 + 1;
	int i = (int) d;
	String n = String.valueOf(i);
	if (i < 10) {
		n = "0" + n;
	}
%>
<img alt="" src="/resources/img/dogs/puppy_dogs_<%=n%>.png">
<br />
还没有写这个页面呢...
