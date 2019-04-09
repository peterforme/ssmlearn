<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<title>小吐槽</title>
<br/>
<br/>


<div class="workingArea">
    <h1 class="label label-info" >来吐槽吧</h1>
    <br>
    <br>
     
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed" width="100%"  style="table-layout:fixed">
            <col style="width: 20%" />
           <col style="width: 65%" />
           <col style="width: 10%" />
           <col style="width: 5%" />
            
            <thead>
                <tr class="success">
                    <th>标题</th>
                    <th>内容</th>
                    <th>图片</th>
                    <c:if test="${isShowDel}">
                    <th>删除</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${cs}" var="c">
                 
                <tr>
                    <td><a class="link" href="complaint_detail?id=${c.id}">${c.title}</a></td>
                    <td>${c.content}</td>     
                    <td><img height="40px" src="img/complaint_small/${c.firstComplaintImage.id}.jpg" onerror="this.style.display='none'"></td>
                    
                    <c:if test="${isShowDel}">
	                    <td>
						        <c:choose>
						        <c:when test="${!empty user && user.id == c.uid}">
						         <a deleteLink="true" href="complaint_delete?id=${c.id}">
						        <span class="   glyphicon glyphicon-trash"></span>
						        </a>
						         </c:when>      
	        					<c:otherwise>无权限</c:otherwise>      
						        </c:choose>
						</td>
					 </c:if>
				    
                    
                    
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
     
    <div class="pageDiv">
        <%@include file="../../../include/admin/adminPage.jsp" %>
    </div>
     
    <div class="panel panel-warning addDiv">
      <div class="panel-heading">新增吐槽</div>
      <div class="panel-body">
            <form method="post" id="addForm" action="complaint_add" enctype="multipart/form-data">
                <table class="addTable">
                    <tr>
                        <td>吐槽标题</td>
                        <td><input  id="title" name="title" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>吐槽内容</td>
                        <td><textarea  id="content" name="content" class="form-control" rows="6"></textarea></td>
                    </tr>
                    <tr>
                        <td>圖片</td>
                        <td>
                            <input id="complaintPic" accept="image/*" type="file" name="image" multiple/>
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
      </div>
    </div>
     
</div>





