<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>创建直联</title>
    <link rel="stylesheet" href="${ctx}/libs/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/libs/manager/files.css">
    <style>
        input, textarea{
            width: 390px;
            height: 25px;
            margin: 0 auto;
            line-height: 20px;
            padding-left: 10px;
            border: 1px solid #e6e6e6;
            color: #333;
        }
    </style>
</head>
<body>
<div class="body">

    <div style="margin: 10px 0 0 10px;">
        直联名称 <input type="text" name="name" placeholder="输入直联名称"/>
        <br/><br/>
        文件路径 <input type="path" name="path" placeholder="输入文件所在百度网盘路径" />
        <br/><br/>
        <div style="margin: 20px 0 0 0;text-align: center;"><button>生成直联</button></div>
    </div>

</div>

</body>
<script type="text/javascript" src="${ctx}/libs/jquery.min.js" ></script>
<script type="text/javascript" src="${ctx}/libs/layer-v3.1.1/layer/layer.js" ></script>
<script>
    $(function(){
        $("button").click(function(){
            var index = parent.layer.msg('正在创建连接，请稍候...', {icon: 16,time: 0,shade : [0.5 , '#000' , true]});
            var data = {
                name: $("input[name='name']").val(),
                filePath: $("input[name='path']").val()
            };
            parent.H.post("${ctx}/baidu/create", data, function (res) {
                parent.layer.close(index);
                var link = window.location.protocol+"//"+window.location.host;
                link += "${ctx}/baidu/link/" + res.responseBody.link;

                parent.layer.alert("百度网盘直链创建成功， 请手动复制下列信息进行保存.<hr/><br/>连接: " + link, function () {
                    parent.layer.closeAll();
                });
            });
        });
    });



</script>
</html>