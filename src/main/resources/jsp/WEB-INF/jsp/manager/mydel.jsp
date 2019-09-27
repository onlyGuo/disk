<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>Files</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/manager/files.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min93e3.css?v=4.4.0">
</head>
<body>
    <div class="head">
		
		<p style="color: dodgerblue;margin:10px;display: inline-block;">回收站文件保留7日后自动销毁。</p>
		
        <div class="workBtns">
            <button class="btnGroup end">还原</button>
        </div>



        <!-- <div class="search">
            <div class="searchBox">
                <input type="text" placeholder="搜索文件或文件夹" class="input-yuan"/>
                <span class="searchIco"><i class="fa fa-search"></i></span>
            </div>
        </div> -->
    </div>
    <hr/>
    <div class="fileList">
        <table cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th width="70%"><div class="chickBox" fileId="all"></div><span class="fielName">文件名</span></th>
                    <th>大小</th>
                    <th>删除日期</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach items="${files }" var="fi">
            		<tr itemId="${fi.fid }">
	                    <td>
	                        <div class="chickBox" fileId="${fi.fid }"></div>
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
	                        <span class="fileWork" hidden id="wk_${fi.fid }">
	                            <a href="javascript:restoreItem(${fi.fid });"><i class="fa fa-share"></i></a>
	                            <a href="javascript:deleteItem(${fi.fid });"><i class="fa fa-trash-o"></i></a>
	                        </span>
	                        
	                    </td>
	                    <td>
	                    	--
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