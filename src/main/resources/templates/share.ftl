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

    <#if (share.password) ??>
        <div style="color: #515151; height: 100%; width: 100%; background-color: rgba(0,0,0,.2);position: absolute;z-index: 10;left: 0;top: 0;">
            <div style="background-color: white; border: #f0f0f0 1px solid; width: 500px; height: 300px; margin: 200px auto auto auto;
                border-radius: 10px;overflow:hidden; box-shadow: gray 2px 2px 54px -7px" >
                <p style="background-color: #f6f6f6; height: 40px; line-height: 40px;">资源分享人设置了密码，您需要输入访问密码才能访问。</p>

                <div style="text-align: center; margin-top: 50px;">
                    请输入访问密码: <input type="text" name="password" style="width: 200px; padding-left: 10px;" />
                    <div style="margin: 50px auto auto auto; text-align: center">
                        <button onclick="setPassword('${share.id}')">确定</button>
                    </div>
                </div>

            </div>

        </div>
    </#if>


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
        <button onclick="download('${share.id}')"><i class="fa fa-download"></i> 下载</button>
        <button><i class="fa fa-hand-stop-o"></i> 举报</button>
    </div>

    <hr/>

    <div class="contentBody">
        <img src="${ctx}/images/file-max.png" class="maxImg">
        <p>大小：${share.size}</p>
        <p>限速：${share.speedDisPlay}</p>
    </div>
</div>
<script src="${ctx}/libs/jquery.min.js"></script>
<script src="${ctx}/libs/layer-v3.1.1/layer/layer.js"></script>
<script src="${ctx}/libs/ajax/core.js"></script>
<script src="${ctx}/libs/layerAjaxMsg/default.js"></script>
<script src="${ctx}/libs/share/share.js"></script>
<script></script>
</body>
</html>