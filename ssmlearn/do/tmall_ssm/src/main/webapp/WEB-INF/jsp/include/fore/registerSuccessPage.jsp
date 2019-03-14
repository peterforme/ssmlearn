<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<script type="text/javascript">
				function countDown(time,url){
				    $("#second").text(time);//<span>中显示的内容值
 					   if(url==''){
 				           url="/";
 							   }
   				 if(--time>0){
        			   setTimeout("countDown("+time+",'"+url+"')",1000);//设定超时时间
   				 }
   				 else{
    			    location.href=url;//跳转页面
   				 }
			}
</script>

<div class="registerSuccessDiv">

	<img src="img/site/registerSuccess.png"> 恭喜注册成功
</div>

<div class="suc">
	<div class="title">
		<span>操作提示</span>
	</div>
	<ul>
		<li>再过<span id="second">5</span>秒后自动跳转<script
				language='javascript'>countDown(5,"loginPage");</script></li>
	</ul>
</div>
