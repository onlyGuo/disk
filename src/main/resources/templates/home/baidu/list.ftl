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
    <button id="createLink" class="default"><i class="fa fa-upload"></i> 创建直联</button>
    <button id="createFolder"><i class="fa fa-folder-open-o"></i> 新建文件夹</button>

    <div class="workBtns">
        <button class="btnGroup">删除</button>
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
            <#list list.list as item >
                <tr id="${item.id}">
                    <td>
                        <div class="chickBox" fileid="${item.id}"></div>
                        <span class="fielName">
                            <i class="fa fa-file-image-o"></i>
                            <a href="javascript:void(0);">${item.name}</a>
                        </span>
                        <span class="fileWork" hidden="" id="wk_${item.id}" style="display: none;">
                            <a href="javascript:download('${item.name}');"><i class="fa fa-cloud-download"></i></a>
                            <a href="javascript:deleteFolder('${item.id}', '${item.name}');"><i class="fa fa-trash-o"></i></a>
                            <a href="javascript:shareFile('${item.name}');"><i class="fa fa-share-alt-square"></i></a>
                        </span>
                    </td>
                    <td>${item.updateTime}</td>
                </tr>
            </#list>

        </tbody>
    </table>
    <div class="more">more</div>
</div>
<div hidden="hidden">
    <input id="ctx" value="${ctx}">
</div>
</body>
<script type="text/javascript" src="${ctx}/libs/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layer-v3.1.1/layer/layer.js" ></script>
<script type="text/javascript" src="${ctx}/libs/ajax/core.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layerAjaxMsg/default.js" ></script>
<#--<script type="text/javascript" src="${ctx}/libs/manager/files.js" ></script>-->

<script>

    $(function(){

        // 创建直联
        $("#createLink").click(function(){
            parent.layer.open({
                type : 2,
                title : "创建百度云盘直联",
                area : [ '500px', '260px' ],
                anim : 2,
                content : ["baidu/create", 'yes' ], // iframe的url，no代表不显示滚动条
            });
        });


    });


</script>


</html>