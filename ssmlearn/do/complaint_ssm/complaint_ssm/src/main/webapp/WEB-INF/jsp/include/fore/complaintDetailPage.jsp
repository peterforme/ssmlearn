<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<title>吐槽详情</title>
<br/>
<br/>


<div class="workingArea">
    <h1 class="label label-info" >来吐槽吧</h1>
    <br>
    <br>
     
     <span class="middleTitle">${c.title}</span>
     <br/>
     
     <c:if test="${isShowEdit}">
     <a class="link" href="forecomplaintedit?id=${c.id}">编辑</a>
     </c:if>
     
     <br/>
     <br/>
     <p class="content">${c.content}</p>
     
     <br/>
     <br/>
     <c:forEach items="${c.complaintImageList}" var="ci">
	     <div>
	     	<img src="img/complaint_middle/${ci.id}.jpg" onerror="this.style.display='none'">
	     </div>
	     <br/>
	     <br/>
     </c:forEach>
     
     <br/>
     <br/>
     <br/>
     <br/>
</div>





