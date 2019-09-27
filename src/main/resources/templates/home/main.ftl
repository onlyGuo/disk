<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理 - ${appName}</title>
    <link rel="stylesheet" href="${ctx}/libs/manager/main.css">
    <link rel="stylesheet" href="${ctx}/libs/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/libs/manager/jquery.spider.disk.css">
</head>
<body>

<div class="head">
    <div class="logo">
        <img src="${ctx}/images/logo-manager.png">
    </div>
    <div class="tab">
        <a href="javascript:void(0)">系统管理</a>
        <a href="javascript:void(0)">收益管理</a>
    </div>
    <div class="right">
        <img src="${LOGIN_USER.avatar}" />
        <span>${LOGIN_USER.nickerName}</span>
        <a href="login/logout">退出</a>
    </div>
</div>
<div class="body">
    <div class="nav">
        <ul>
            <li class="active" addr="files/list/"><i class="fa fa-folder"></i> 全部文件</li>
            <li addr="myshare"><i class="fa fa-share"></i> 我的分享</li>
        </ul>
    </div>

    <iframe src="files/list/" height="100%" width="100%" id="workIframe">

    </iframe>
</div>



</body>
<script type="text/javascript" src="${ctx}/libs/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layer-v3.1.1/layer/layer.js" ></script>
<script type="text/javascript" src="${ctx}/libs/manager/main.js" ></script>
</html>