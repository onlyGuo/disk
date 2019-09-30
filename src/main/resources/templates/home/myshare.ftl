<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>Files</title>
    <link rel="stylesheet" href="${ctx}/libs/manager/files.css">
    <link rel="stylesheet" href="${ctx}/libs/fonts/font-awesome.min.css">
</head>
<body>
<div class="head">
    <div class="workBtns">
        <button class="btnGroup share">取消分享</button>
    </div>

    <div class="search">
        <div class="searchBox">
            <input type="text" placeholder="搜索文件或文件夹" class="input-yuan"/>
            <span class="searchIco"><i class="fa fa-search"></i></span>
        </div>
    </div>
</div>
<hr/>
<div class="fileList">
    <table cellpadding="0" cellspacing="0">
        <thead>
        <tr>
            <th width="50%"><div class="chickBox" fileId="all"></div><span class="fielName">文件名</span></th>
            <th>分享密码</th>
            <th>下载限速</th>
            <th>下载付费</th>
            <th>修改日期</th>
        </tr>
        </thead>
        <tbody>
        <#list files as fi>
            <tr itemId="{id}">
                <td>
                    <div class="chickBox" fileId="${fi.id}"></div>
                    <span class="fielName">
                    <i class="fa fa-folder-o"></i>
                    <a href="javascript:void(0);">${fi.name}</a>
	            </span>
                    <span class="fileWork" hidden id="wk_${fi.id }">
                    <a href="javascript:deleteShare('${fi.id }');" title="取消分享"><i class="fa fa-chain-broken"></i></a>
                    <a href="javascript:lookShare('${fi.id }', this);" title="查看分享地址"><i class="fa fa-eye"></i></a>
                </span>
                </td>
                <td> ${fi.password! ''}</td>
                <td> ${fi.speed ! '无限制'} kb/s</td>
                <td> ${fi.amount ! '免费'} ￥</td>
                <td>${fi.createTime}</td>

            </tr>
        </#list>
        </tbody>
    </table>
</div>
<div hidden="hidden">
    <input id="list_dir" value="${pathName}" />
    <input id="list_api_dir" value="${ctx}/files/list">
    <input id="ctx" value="${ctx}">
    <input id="list_make" value="">
</div>
</body>
<script type="text/javascript" src="${ctx}/libs/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layer-v3.1.1/layer/layer.js" ></script>
<script type="text/javascript" src="${ctx}/libs/ajax/core.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layerAjaxMsg/default.js" ></script>
<script type="text/javascript" src="${ctx}/libs/manager/myshare.js" ></script>
</html>