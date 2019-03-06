<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
 
<script>
    $(function(){
 
        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.loginErrorMessageDiv").show();
        </c:if>
 
        $("form.loginForm").submit(function(){
            if(0==$("#name").val().length||0==$("#password").val().length){
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });
 
        $("form.loginForm input").keyup(function(){
            $("div.loginErrorMessageDiv").hide();
        });
 
        var left = window.innerWidth/2+162;
        $("div.loginSmallDiv").css("left",left);
        
        //获取cookie应该写在前端还是后端？ 下面两段分别为前端获取和后端获取（个人觉得写在后端比较好如果需要序列化）
        var cookiestr = getCookie("backuser");
        if(cookiestr != ""){
        	cookiestr = cookiestr.substring(1,cookiestr.length-1);
        	var username = cookiestr.split(",")[0];
        	var password = cookiestr.split(",")[1];
        	$("#name").val(username);
        	$("#password").val(password);
        	$("#needRem").prop("checked",true);
        }
        
        <c:if test="${!empty cookieVal}">
        var cookiestr = "${cookieVal}";
        alert(cookiestr);
        var username = cookiestr.split(",")[0];
    	var password = cookiestr.split(",")[1];
    	$("#name").val(username);
    	$("#password").val(password);
    	$("#needRem").prop("checked",true);
        </c:if>
        
        
    })
    
    function getCookie(cookiename){
    	var name = cookiename + "=";
    	var str = document.cookie.split(';');
    	var le = str.length;
    	for(var i =0; i<str.length; i++){
    		var ind = str[i];
    		while(ind.charAt(0)==' ')
    			ind = ind.substring(1);
    		var saf = ind.length;
    		console.log(ind);
    		if(ind.indexOf(name) != -1)
    			return ind.substring(name.length,ind.length);
    	}
    	return "";
    };
    
</script>
 
<div id="loginDiv" style="position: relative">
 
    <div class="simpleLogo">
        <a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
    </div>
 
    <img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">
 
    <form class="loginForm" action="admin_backuser_login" method="post">
        <div id="loginSmallDiv" class="loginSmallDiv">
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger" >
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                    <span class="errorMessage"></span>
                </div>
            </div>
 
            <div class="login_acount_text">账户登录</div>
            <div class="loginInput " >
                <span class="loginInputIcon ">
                    <span class=" glyphicon glyphicon-user"></span>
                </span>
                <input id="name" name="name" placeholder="手机/会员名/邮箱" type="text">
            </div>
 
            <div class="loginInput " >
                <span class="loginInputIcon ">
                    <span class=" glyphicon glyphicon-lock"></span>
                </span>
                <input id="password" name="password" type="password" placeholder="密码" type="text">
            </div>
            
            <input id="needRem" name="needRem" type="checkbox" >记住密码 </input>
            
            <span class="text-danger">不要输入真实的天猫账号密码</span><br><br>
 
            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="registerPage" class="pull-right">免费注册</a>
            </div>
            <div style="margin-top:20px">
                <button class="btn btn-block redButton" type="submit">登录</button>
            </div>
        </div>
    </form>
 
</div>   