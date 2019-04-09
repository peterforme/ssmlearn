<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
 
<nav class="top ">
    <a href="forehome">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        小吐槽首页
    </a>
 
    <span>喵，欢迎来吐槽</span>
 
    <c:if test="${!empty user}">
        <span>${user.name}</span>
        <a href="forelogout">退出</a>
    </c:if>
 
    <c:if test="${empty user}">
        <a href="loginPage">请登录</a>
        <a href="registerPage">免费注册</a>
    </c:if>
 
 	<c:if test="${!empty user}">
        <span class="pull-right">
            <a href="foremycomplaint">我的吐槽</a>
        </span>
    </c:if>
 
    
 
</nav>