<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>首页 - 爱易云盘</title>
    <meta name="baidu-site-verification" content="7zVpEiOYTe" />
    <meta name="360-site-verification" content="ca89dc775319ba1522ab6c2931acc302" />
    <meta name="keywords" content="网盘,disk,不吞连,永久,分享,上传,下载,存储,不限速" />
    <meta name="description" content="爱易云盘是爱易编程网对外开放的文件存储与文件分享网盘。主打不随意吞链、不限制分享时长、不限制下载速度、不强制绑定客户端的四大特色。" />
    <meta name="author" content="爱易编程网">
    <link rel="stylesheet" type="text/css" href="css/login.css"/>
</head>
<body>
    <div id="login">
    <h1>登陆爱易云盘</h1>
    <form method="post" action="manager">
        <input type="text" required="required" placeholder="用户名" id="username" />
        <input type="password" required="required" placeholder="密码" id="password" />
        <button class="but" type="button" onclick="login();">登录</button>
    </form>
</div>
</body>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/layer/layer.js"></script>
<script type="text/javascript">
	function login(){
		var request = {
				username:$("#username").val(),
				password:$("#password").val()
		}
		if(!request.username || !request.password){
			layer.msg("请填写账号或密码");
			return;
		}
		$.post("login", request, function(response){
			if(response.result){
				location = "manager/main";
			}else{
				layer.msg(response.message);
			}
		}).error(function(){
			layer.msg("登陆出错");
		});
		
	}
</script>
</html>