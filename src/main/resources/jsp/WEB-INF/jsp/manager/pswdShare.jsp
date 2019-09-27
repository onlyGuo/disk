<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
        body, button, input, select, textarea {
            color: rgb(102, 102, 102);
            /* font: 14px/1.5 "Microsoft YaHei", arial, SimSun, 宋体; */
        }
        /* input, button, select {
            margin: 0;
            line-height: 1.2;
        } */
        input {
            -webkit-appearance: textfield;
            background-color: white;
            -webkit-rtl-ordering: logical;
            -webkit-user-select: text;
            cursor: auto;
            padding: 1px;
            border-width: 2px;
            border-style: inset;
            border-color: initial;
            border-image: initial;
            color: #818796;
        }
        input, textarea, keygen, select, button {
            text-rendering: auto;
            color: initial;
            letter-spacing: normal;
            word-spacing: normal;
            text-transform: none;
            text-indent: 0px;
            text-shadow: none;
            display: inline-block;
            text-align: start;
            margin: 0em 0em 0em 0em;
            font: 13.3333px Arial;
        }
        input, textarea, keygen, select, button, meter, progress {
            -webkit-writing-mode: horizontal-tb;
        }
        
        .Window{
            height: 200px;
            width: 450px;
            color: #4ba6ff;
            margin: 10px;
        }
        .Window .link{
            width: 100%;
            height: 60px;
            margin-top: 30px;
        }
        .Window .link input{
            height: 19px;
            line-height: 18px;
            width: 350px;
            padding: 8px;
            border: 1px solid #e9e9e9;
            border-radius: 4px;
            display: inline-block;
            outline: 0;
            resize: none;
        }
        .Window .link input.url{
        	width: 300px;
        }
        .Window .link input.pswd{
        	width: 30px;
        }
        .Window .link button{
             border: 1px solid #3b8cff;
             background-color: #3b8cff;
             color: white;
             border-radius: 4px;
             outline: 0;
             height: 34px;
             display: inline-block;
             padding: 0 5px;
         }
        .Window .link button:hover{
            background-color: #4a77d4;
        }
    </style>
</head>
<body>
    <div class="Window">
        <i class="fa fa-check-circle"></i> 成功创建私密链接
        <div class="link">
            <input class="url" type="text" value="http://pan.321aiyi.com/share/${shareFile.responseBody.id }">
            <input class="pswd" type="text" value="${shareFile.responseBody.pswd }">
            <button onClick="copyUrl('地址:http://pan.321aiyi.com/share/${shareFile.responseBody.id } 密码:${shareFile.responseBody.pswd }')">复制链接</button>
        </div>
        1.生成文件下载链接
        <br/>
        2.把链接通过QQ、微博、人人网、QQ空间等方式分享给好友
        <div>
            <p style="color: gray">
                配合净网行动，爱易云盘严厉打击不良信息、色情低俗信息的传播行为
            </p>
        </div>
    </div>
    <script type="text/javascript">
    	function copyUrl(url){
    		try{
    			  // 创建元素用于复制
    		      var aux = document.createElement("input");

    		      // 获取复制内容
    		      var content = url;
    		    
    		      // 设置元素内容
    		      aux.setAttribute("value", content);
    		    
    		      // 将元素插入页面进行调用
    		      document.body.appendChild(aux);
    		    
    		      // 复制内容
    		      aux.select();
    		    
    		      // 将内容复制到剪贴板
    		      document.execCommand("copy");
    		    
    		      // 删除创建元素
    		      document.body.removeChild(aux);	
    		      
    		      layer.msg("已复制");
			}catch (e) {
				// TODO: handle exception
				layer.alert("该浏览器不支持该操作，请手动复制以下链接：" + url);
			}
    	}
    </script>
</body>
</html>