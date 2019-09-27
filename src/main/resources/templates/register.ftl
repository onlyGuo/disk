<!DOCTYPE html>
<html lang="zh-CN"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>注册 - ${appName}</title>
    <meta name="keywords" content="网盘,disk,不吞连,永久,分享,上传,下载,存储,不限速">
    <meta name="description" content="蟑螂云盘，这是一个不正经的网盘，不限速，不吞链，不限容量！本系统只负责提供GUI操作界面，你的资源完全由你自己掌控！甚至可以设置某些文件的付费下载，以此带来收益。">

    <link rel="stylesheet" type="text/css" href="./libs/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="./libs/fonts/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="./libs/fonts/material-design-iconic-font.min.css">
    <link rel="stylesheet" type="text/css" href="./libs/util.css">
    <link rel="stylesheet" type="text/css" href="./libs/main.css">
</head>

<body>

<div class="limiter">
    <div class="container-login100" style="background-image: url(&#39;images/bg-01.jpg&#39;);">
        <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
            <form class="login100-form validate-form">
                <span class="login100-form-title p-b-49">注册</span>

                <div class="wrap-input100 validate-input m-b-23 alert-validate" data-validate="请输入用户名">
                    <span class="label-input100">用户名</span>
                    <input class="input100" type="text" name="username" placeholder="请输入用户名" autocomplete="off">
                    <span class="focus-input100" data-symbol=""></span>
                </div>

                <div class="wrap-input100 validate-input m-b-23 alert-validate" data-validate="请输入密码">
                    <span class="label-input100">密码</span>
                    <input class="input100" type="password" name="password" placeholder="请输入密码">
                    <span class="focus-input100" data-symbol=""></span>
                </div>

                <div class="wrap-input100 validate-input m-b-23 alert-validate" data-validate="请输入密码">
                    <span class="label-input100">确认密码</span>
                    <input class="input100" type="password" name="pass" placeholder="请输入密码">
                    <span class="focus-input100" data-symbol=""></span>
                </div>

                <div class="wrap-input100 validate-input m-b-23 alert-validate" data-validate="请输入电子邮箱">
                    <span class="label-input100">电子邮箱</span>
                    <input class="input100" type="email" name="email" placeholder="请输入电子邮箱" style="padding-left: 7px;">
                </div>

                <div class="wrap-input100 validate-input m-b-23 alert-validate" data-validate="请输入OSS AccessKey">
                    <span class="label-input100">AccessKey ID</span>
                    <input class="input100" type="text" name="accessKey" placeholder="请输入OSS AccessKey" style="padding-left: 7px;">
                </div>

                <div class="wrap-input100 validate-input m-b-23 alert-validate" data-validate="请输入AccessKeySecret">
                    <span class="label-input100">AccessKey Secret</span>
                    <input class="input100" type="text" name="accessKeySecret" placeholder="请输入AccessKeySecret" style="padding-left: 7px;">
                </div>

                <div class="wrap-input100 validate-input m-b-23 alert-validate" data-validate="请输入OSS外网节点">
                    <span class="label-input100">endPoint</span>
                    <input class="input100" type="text" name="endPoint" placeholder="请输入OSS外网节点" style="padding-left: 7px;">
                </div>

                <div class="wrap-input100 validate-input alert-validate" data-validate="请输入Bucket">
                    <span class="label-input100">Bucket</span>
                    <input class="input100" type="text" name="bucket" placeholder="请输入Bucket" style="padding-left: 7px;">
                </div>

                <div class="text-right p-t-8 p-b-31">
                    <a href="login">已有帐号？</a>
                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn" act="register">注 册</button>
                    </div>
                </div>
                <div class="flex-col-c p-t-25">
                    <a href="login" class="txt2">立即登录</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${ctx}/libs/jquery.min.js"></script>
<script src="${ctx}/libs/layer-v3.1.1/layer/layer.js"></script>
<script src="${ctx}/libs/main.js"></script>
<script src="${ctx}/libs/ajax/core.js"></script>
<script src="${ctx}/libs/layerAjaxMsg/default.js"></script>

<script>

    function doSubmit(){

        var data = {
            username: $("input[name='username']").val(),
            password: $("input[name='password']").val(),
            pass: $("input[name='pass']").val(),
            email: $("input[name='email']").val(),
            accessKey: $("input[name='accessKey']").val(),
            accessKeySecret: $("input[name='accessKeySecret']").val(),
            bucket: $("input[name='bucket']").val(),
            endPoint: $("input[name='endPoint']").val()
        };
        if (data.password !== data.pass){
            layer.alert("两次密码输入不一致");
            return;
        }


        H.post("${ctx}/register", data, function (res){
            location.href = "login";
        })
    }

</script>
</body></html>