

/**
 * 获取文件
 * @param fileId
 * @returns
 */
function download(fileId, t){
	var $t = $(t);
	$t.css("disable", "disable");
	$t.html("处理中...");
	H.post(fileId + "/download", {}, function (res) {
		if (res.success){
			window.open(res.responseBody.link);
		}else{
			$(".pay-tab-box").css("display", "block");
			$(".pay-tab-box a").attr("href", "javascript:openPayQrCode('" + res.responseBody.rqCodeContent + "');");
		}
		$t.css("disable", null);
		$t.html("下载");
	}, function(res){
		alert(res.message);
		$t.css("disable", null);
		$t.html("下载");
	});
}

/**
 * 打开支付二维码窗口
 * @param content
 * 		二维码内容
 */
function openPayQrCode(content){
	var id = "qrcode_" + new Date().getTime();
	layer.alert("<div style='text-align: center;'><p>请使用支付扫码进行付款, 付款完后页面会自动跳转.如果没有自动跳转请点击" +
		"确定再尝试下载.</p><br/><div style='display: inline-block;' id='" + id + "'></div></div>");
	var qrcode = new QRCode(id, {
		text: content,
		width: 200,
		height: 200,
		colorDark : "#000000",
		colorLight : "#ffffff",
		correctLevel : QRCode.CorrectLevel.H
	});
}

/**
 * 输入分享密码
 * @param fileId
 */
function setPassword(fileId){
	H.post("inputPswd", {
		id: fileId,
		password: $("input[name='password']").val()
	}, function (res) {
		location.href = location.href;
	});
}