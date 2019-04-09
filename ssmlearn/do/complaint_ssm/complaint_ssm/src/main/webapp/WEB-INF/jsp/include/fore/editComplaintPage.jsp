<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<head>
<script>
//虽然header处有调用过ready事件，这里继续调用也是可以的
$(function(){
	//String类型
	var a = "${!empty jump}";
     if(a == "true"){
    	 window.location.hash = "#jumpPosition";
     }
     
});

function gogogo(){
	$("#twoForm").attr("action","forecomplaintupdate");
    $("#twoForm").submit();
 }

</script>
</head>

<title>吐槽详情</title>
<br/>
<br/>


<div class="workingArea">
    <h1 class="label label-info" >来吐槽吧</h1>
    <br>
    <br>
    <form id="twoForm" method="post" class="addFormSingle" action="complaintImageAdd" enctype="multipart/form-data">
     
     <input  id="editTitle" name="title" value="${c.title}" type="text" class="form-control">
     <br>
     <br>
     <textarea  id="editContent" name="content"  class="form-control" rows="30">${c.content}</textarea>
     <br/>
     <br/>
     
     <c:forEach items="${c.complaintImageList}" var="ci">
	     <div>
	     	<img src="img/complaint_middle/${ci.id}.jpg" onerror="this.style.display='none'">
	     </div>
	     <br/>
	     <br/>
     </c:forEach>
     
     
     <table class="addPictureTable" align="center">
        <tr>
            <td class="addPictureTableTD">
                <div>
                    
                    <table id="jumpPosition" class="table table-striped table-bordered table-hover  table-condensed">
                        <thead>
                        <tr class="success">
                            <th>图片</th>
                            <th>删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${c.complaintImageList}" var="ci">
                            <tr>
                                <td>
                                    <a title="点击查看原图" href="img/complaint/${ci.id}.jpg"><img height="50px" src="img/complaint_small/${ci.id}.jpg"></a>
                                </td>
                                <td><a deleteLink="true"
                                       href="complaintImageDelete?id=${ci.id}"><span
                                        class="     glyphicon glyphicon-trash"></span></a></td>
 
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
 
 
 					<div class="panel panel-warning addPictureDiv">
                        <div class="panel-heading">新增<b class="text-primary"> 图片 </b></div>
                        <div class="panel-body">
                            
                                <table class="addTable">
                                    <tr>
                                        <td>请选择本地图片 尺寸400X400 为佳</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input id="complaintPic" accept="image/*" type="file" name="image" multiple />
                                        </td>
                                    </tr>
                                    <tr class="submitTR">
                                        <td align="center">
                                            <input type="hidden" name="id" value="${c.id}" />
                                            <input type="hidden" name="uid" value="${user.id}" />
                                            <button type="submit" class="btn btn-success">提 交</button>
                                        </td>
                                    </tr>
                                </table>
                        </div>
                    </div>
 
                </div>
            </td>
        </tr>
    </table>
     
     
     <br/>
     <br/>
     
     <div class="text-center">
     <button type="button" class="btn btn-default left-btn" onclick="window.history.go(-1)">取消</button>
     <button type="button" class="btn btn-primary right-btn" onclick="gogogo()">提交</button>
     </div>
	</form>
     
     <br/>
     <br/>
     <br/>
     <br/>
     
     
</div>





