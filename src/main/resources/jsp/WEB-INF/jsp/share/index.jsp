<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>${share.responseBody.shareFile.name } - ${share.responseBody.shareUser.username }的分享 - 爱易云盘</title>
    <link rel="stylesheet" href="../css/share/share.css">
    <link rel="stylesheet" href="../css/font-awesome.min93e3.css?v=4.4.0">
    <!-- 请置于所有广告位代码之前 -->
	<script src="http://dup.baidustatic.com/js/ds.js"></script>
</head>
<body>
    <div class="head">
        <div class="logo">
            <img src="../img/logo-manager.png">
        </div>

        <div class="left">
            <img src="http://www.321aiyi.com/uc_server/avatar.php?uid=${share.responseBody.shareUser.uid }&size=middle" class="userHeadImg">
            ${share.responseBody.shareUser.username }
            <i class="fa fa-sort-desc"></i>
            <a href="http://www.321aiyi.com/">进入爱易编程网</a>
            <div class="user_tab_box">
                <div class="userTab">
                    <div class="sanJiao"></div>
                    <div class="userTabHead">
                        <img src="http://www.321aiyi.com/uc_server/avatar.php?uid=${share.responseBody.shareUser.uid }&size=middle" class="userHeadImg">
                        ${share.responseBody.shareUser.username }
                    </div>
                    <div class="userTabBody">
                        <div class="goHome">
                            查看他的主页<a href="http://www.321aiyi.com/space-uid-${share.responseBody.shareUser.uid }.html">他的主页</a>
                        </div>

                        <ul>
                            <li><a href="http://www.321aiyi.com/home.php?mod=space&uid=${share.responseBody.shareUser.uid }&do=thread&view=me&from=space">他的帖子</a></li>
                            <li><a href="http://www.321aiyi.com/home.php?mod=space&do=thread&view=me&type=reply&uid=${share.responseBody.shareUser.uid }&from=space">他的回复</a></li>
                            <li><a href="http://www.321aiyi.com/home.php?mod=space&uid=${share.responseBody.shareUser.uid }&do=profile">查看资料</a></li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </div>
	
		<!-- 广告位：下载页广告 -->
		<div style="width:760px; margin: 20px auto 0 auto;">
			<script>
				(function() {
				    var s = "_" + Math.random().toString(36).slice(2);
				    document.write('<div id="' + s + '"></div>');
				    (window.slotbydup=window.slotbydup || []).push({
				        id: '3280244',
				        container: s,
				        size: '760,90',
				        display: 'inlay-fix'
				    });
				})();
			</script>
		</div>
	
	
    <div class="content">
        <div class="left">
            <img src="../img/file.png" class="minImg">
            <h3>${share.responseBody.shareFile.name }</h3>
            <div class="time">
                <i class="fa fa-calendar-minus-o"></i> ${share.responseBody.shareFile.shareTimeStr }
                <a href="javascript:void(0);">赞(0)</a>
                <a href="javascript:void(0);">评论(0)</a>
                <a href="javascript:void(0);">分享</a>
            </div>
        </div>
        <div class="right">
            <button onClick="download(${share.responseBody.shareFile.id })"><i class="fa fa-download"></i> 下载</button>
            <button><i class="fa fa-hand-stop-o"></i> 举报</button>
        </div>

        <hr/>

        <div class="contentBody">
            <img src="../img/file-max.png" class="maxImg">
            <c:if test="${share.responseBody.shareFile.size == 0 }">
           		<p>大小：--</p>
           	</c:if>
           	<c:if test="${share.responseBody.shareFile.size > 0 && share.responseBody.shareFile.size < 1024}">
           		<p>大小：${share.responseBody.shareFile.size}B</p>
           	</c:if>
           	<c:if test="${share.responseBody.shareFile.size > 1024 && share.responseBody.shareFile.size < 1024 * 1024}">
           		<p>大小：<fmt:formatNumber type="number" value="${share.responseBody.shareFile.size / 1024}" pattern="0.00" maxFractionDigits="2"/>KB</p>
           	</c:if>
           	<c:if test="${share.responseBody.shareFile.size > 1024 * 1024}">
           		<p><fmt:formatNumber type="number" value="${share.responseBody.shareFile.size / 1024 / 1024}" pattern="0.00" maxFractionDigits="2"/>MB</p>
           	</c:if>
        </div>

    </div>
    <div hidden="hidden">
    	<input id="rootPath" value="${pageContext.request.contextPath}">
    </div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/share/share.js" ></script>
</html>