<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>${share.name} - ${share.nickerName}的分享 - ${appName}</title>
    <link rel="stylesheet" href="${ctx}/libs/share/share.css">
    <link rel="stylesheet" href="${ctx}/libs/fonts/font-awesome.min.css">
</head>
<body>
<div class="head">
    <div class="logo">
        <img src="${ctx}/images/logo-manager.png">
    </div>

    <div class="left">
        <img src="${share.avatar}" class="userHeadImg">
        ${share.nickerName}
        <i class="fa fa-sort-desc"></i>
        <a href="http://www.321aiyi.com/">进入爱易编程网</a>
        <div class="user_tab_box">
            <div class="userTab">
                <div class="sanJiao"></div>
                <div class="userTabHead">
                    <img src="${share.avatar}" class="userHeadImg">
                    ${share.nickerName}
                </div>
                <div class="userTabBody">
                    <div class="goHome">
                        查看他的主页<a href="index.html">他的主页</a>
                    </div>

                    <ul>
                        <li><a href="javascript:void(0)">他的帖子</a></li>
                        <li><a href="javascript:void(0)">他的回复</a></li>
                        <li><a href="javascript:void(0)">查看资料</a></li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="content">
    <div class="left">
        <img src="${ctx}/images/file.png" class="minImg">
        <h3>${share.name}</h3>
        <div class="time">
            <i class="fa fa-calendar-minus-o"></i> ${share.createTime}
            <a href="javascript:void(0);">赞(0)</a>
            <a href="javascript:void(0);">评论(0)</a>
            <a href="javascript:void(0);">分享</a>
        </div>
    </div>
    <div class="right">
        <button><i class="fa fa-download"></i> 下载</button>
        <button><i class="fa fa-hand-stop-o"></i> 举报</button>
    </div>

    <hr/>

    <div class="contentBody">
        <img src="${ctx}/images/file-max.png" class="maxImg">
        <p>大小：${share.size}</p>
    </div>

</div>

</body>
</html>