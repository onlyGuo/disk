<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>后台管理 - 爱易云盘</title>
    <link rel="stylesheet" href="../css/manager/main.css">
    <link rel="stylesheet" href="../css/font-awesome.min93e3.css?v=4.4.0">
    <link rel="stylesheet" href="../css/manager/jquery.spider.disk.css">
</head>
<body>

    <div class="head">
        <div class="logo">
            <img src="../img/logo-manager.png">
        </div>
        <div class="tab">
            <a href="javascript:void(0)">网盘</a>
            <a href="javascript:void(0)">分享圈子</a>
        </div>
        <div class="right">
        	<img src="http://www.321aiyi.com/uc_server/avatar.php?uid=${user.uid }&size=middle" />
        	<span>${user.username }</span>
        	<a href="logout">退出</a>
        </div>
    </div>
    <div class="body">
        <div class="nav">
            <ul>
                <li class="active" addr="files"><i class="fa fa-folder"></i> 全部文件</li>
                <li addr="myshare"><i class="fa fa-share"></i> 我的分享</li>
                <li addr="mydel"><i class="fa fa-trash"></i> 回收文件</li>
            </ul>
			<div id="yiBiao">
				<div style="width: ${pan.hascapacity / pan.panKongJian *100}%">
					<span>容量:${pan.hascapacity }/${pan.panKongJian }(M)</span>
				</div>
			</div>            
        </div>

        <iframe src="files" height="100%" width="100%" id="workIframe">

        </iframe>
    </div>



</body>
<script type="text/javascript" src="../js/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js" ></script>
<script type="text/javascript" src="../js/manager/main.js" ></script>
</html>