<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>分享文件</title>
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
        访问密码 <input type="text" name="password" placeholder="设置资源访问密码，若不填则允许直接访问"/>
        <br/><br/>
        下载限速 <input type="number" name="speed" placeholder="设置下载限速(kb)，若不填或填0则不限速" />
        <br/><br/>
        付费下载 <input type="number" name="amount" placeholder="设置下载费用,保留两位小数,若不填则免费" />
        <div style="margin: 20px 0 0 0;text-align: center;"><button>生成分享连接</button></div>
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
                fileKey: "${fileKey}",
                password: $("input[name='password']").val(),
                speed: $("input[name='speed']").val(),
                amount: $("input[name='amount']").val()
            };
            parent.H.post("${ctx}/share", data, function (res) {
                alert("分享成功");
            });
        });
    });



</script>
</html>