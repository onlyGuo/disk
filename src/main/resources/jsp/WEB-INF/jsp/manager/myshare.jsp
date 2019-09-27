<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>MyShare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/manager/files.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min93e3.css?v=4.4.0">
</head>
<body>
    <div class="head">
        <!-- <button id="uploadFile" class="default"><i class="fa fa-upload"></i> 上传</button>
        <button id="createFolder"><i class="fa fa-folder-open-o"></i> 新建文件夹</button> -->

        <div class="workBtns">
            <button class="btnGroup start">下载</button>
            <button class="btnGroup">取消分享</button>
            <button class="btnGroup end">设置密码</button>
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
            	<c:forEach items="${files }" var="fi">
            		<tr itemId="${fi.id }">
	                    <td>
	                        <div class="chickBox" fileId="${fi.id }"></div>
	                        <span class="fielName">
	                            <c:if test="${fi.type == 0 }">
	                            	<i class="fa fa-folder-o"></i>
	                            	<a href="javascript:void(0);">${fi.name }</a>
	                            </c:if>
	                            <c:if test="${fi.type != 0 }">
	                            	<c:if test="${fi.suffix=='zip' || fi.suffix=='rar' || fi.suffix=='tg' || fi.suffix=='7z' || fi.suffix=='cab' || fi.suffix=='z' || fi.suffix=='jar' || fi.suffix=='7-zip' || fi.suffix=='gzip' || fi.suffix=='iso' }">
		                            	<i class="fa fa-file-zip-o"></i>
	                            	</c:if>
	                            	<c:if test="${fi.suffix=='jpg' || fi.suffix=='jpeg' || fi.suffix=='gif' || fi.suffix=='png' || fi.suffix=='bmp' }">
		                            	<i class="fa fa-file-image-o"></i>
	                            	</c:if>
	                            	<c:if test="${fi.suffix=='avi' || fi.suffix=='mp4' || fi.suffix=='flv' || fi.suffix=='rmvb' || fi.suffix=='wma' }">
		                            	<i class="fa fa-file-video-o"></i>
	                            	</c:if>
	                            	<c:if test="${fi.suffix=='txt' || fi.suffix=='doc' || fi.suffix=='docx' || fi.suffix=='pdf' }">
		                            	<i class="fa fa-file-text-o"></i>
	                            	</c:if>
	                            	
	                            	<c:if test="${fi.suffix!='zip' && fi.suffix!='rar' && fi.suffix!='tg' && fi.suffix!='7z' 
	                            	&& fi.suffix!='cab' && fi.suffix!='z' && fi.suffix!='jar' && fi.suffix!='7-zip' 
	                            	&& fi.suffix!='gzip' && fi.suffix!='iso'
	                            	&& fi.suffix!='jpg' && fi.suffix!='jpeg' && fi.suffix!='gif' && fi.suffix!='png' && fi.suffix!='bmp'
	                            	&& fi.suffix!='avi' && fi.suffix!='mp4' && fi.suffix!='flv' && fi.suffix!='rmvb' && fi.suffix!='wma'
	                            	&& fi.suffix!='txt' && fi.suffix!='doc' && fi.suffix!='docx' && fi.suffix!='pdf' }">
		                            	<i class="fa fa-file-o"></i>
	                            	</c:if>
	                            	
	                            	<a href="javascript:void(0);">${fi.name }</a>
	                            </c:if>
	                        </span>
	                        <c:if test="${fi.sharePswd==null }">
	                        	<span class="fileWork" hidden id="wk_${fi.id }">
		                            <a href="javascript:download(${fi.id });" title="下载"><i class="fa fa-cloud-download"></i></a>
		                            <a href="javascript:deleteShare(${fi.id });" title="取消分享"><i class="fa fa-chain-broken"></i></a>
		                            <a href="javascript:createPassword(${fi.id });" title="设置密码"><i class="fa fa-lock"></i></a>
		                            <a href="javascript:shareFile(${fi.id });" title="查看分享地址"><i class="fa fa-eye"></i></a>
		                        </span>
	                        </c:if>
	                        <c:if test="${fi.sharePswd!=null }">
	                        	<span class="fileWork" hidden id="wk_${fi.id }">
		                            <a href="javascript:download(${fi.id });" title="下载"><i class="fa fa-cloud-download"></i></a>
		                            <a href="javascript:deleteShare(${fi.id });" title="取消分享"><i class="fa fa-chain-broken"></i></a>
		                            <a href="javascript:deletePassword(${fi.id });" title="取消分享密码"><i class="fa fa-unlock"></i></a>
		                            <a href="javascript:shareFile(${fi.id });" title="查看分享地址"><i class="fa fa-eye"></i></a>
		                        </span>
	                        </c:if>
	                        
	                        
	                    </td>
	                    <td>
	                    	<c:if test="${fi.size == 0}">
	                    		--
	                    	</c:if>
	                    	<c:if test="${fi.size > 0 && fi.size < 1024}">
	                    		${fi.size}B
	                    	</c:if>
	                    	<c:if test="${fi.size > 1024 && fi.size < 1024 * 1024}">
	                    		<fmt:formatNumber type="number" value="${fi.size / 1024}" pattern="0.00" maxFractionDigits="2"/>KB
	                    	</c:if>
	                    	<c:if test="${fi.size > 1024 * 1024 && fi.size < 1024 * 1024 * 1024}">
	                    		<fmt:formatNumber type="number" value="${fi.size / 1024 / 1024}" pattern="0.00" maxFractionDigits="2"/>MB
	                    	</c:if>
	                    </td>
	                    <td>${fi.updateTimeStr }</td>
	                </tr>
            	</c:forEach>
            </tbody>

        </table>
    </div>
    <div hidden="hidden">
    	<c:if test="${parentId!= null}">
    		<input id="thisPage" value="${pageContext.request.contextPath}/manager/files/${parentId}" />
    	</c:if>
    	<c:if test="${parentId== null}">
    		<input id="thisPage" value="${pageContext.request.contextPath}/manager/files" />
    	</c:if>
    	<input id="rootPage" value="${pageContext.request.contextPath}" />
    </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/manager/files.js" ></script>
</html>