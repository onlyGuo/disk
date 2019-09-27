<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>Files</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/style.css"/>
</head>
<body>
	<h4>您所选择的文件列表：</h4>
	<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>
	
	<br/>
	
	<div id="container">
		<a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
		<a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
	</div>
	
	<pre id="console"></pre>
	
	<p>&nbsp;</p>
	<div hidden="hidden">
		<input id="rootPath" value="${pageContext.request.contextPath}">
		<input id="parentId" value="${parentId}">
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/base64.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/upload.js"></script>

</html>