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
    <button id="uploadFile" class="default"><i class="fa fa-upload"></i> 上传</button>
    <button id="createFolder"><i class="fa fa-folder-open-o"></i> 新建文件夹</button>

    <div class="workBtns">
        <button class="btnGroup start">下载</button>
        <button class="btnGroup">删除</button>
        <button class="btnGroup end">分享</button>
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
            <th width="70%"><div class="chickBox" fileId="all"></div><span class="fielName">文件名</span></th>
            <th>大小</th>
            <th>修改日期</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
    <div class="more">more</div>
</div>
<div hidden="hidden">
    <input id="list_dir" value="${pathName}" />
    <input id="list_api_dir" value="${ctx}/files/list">
    <input id="list_make" value="">
</div>
</body>
<script type="text/javascript" src="${ctx}/libs/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layer-v3.1.1/layer/layer.js" ></script>
<script type="text/javascript" src="${ctx}/libs/ajax/core.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layerAjaxMsg/default.js" ></script>
<script type="text/javascript" src="${ctx}/libs/manager/files.js" ></script>
</html>