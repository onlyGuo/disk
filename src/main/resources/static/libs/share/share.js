
var spuFileId = null;
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
			spuFileId = fileId;
			$("#payAmount").html(res.responseBody.amount);
			$(".pay-tab-box a").attr("href", "javascript:openPayQrCode('" + res.responseBody.rqCodeContent + "', '" + res.responseBody.orderNo + "');");
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
function openPayQrCode(content, orderNo){
	var id = "qrcode_" + orderNo;
	layer.alert("<div style='text-align: center;'><p>请使用支付扫码进行付款, 付款完后页面会自动跳转.如果没有自动跳转请点击" +
		"确定再尝试下载.</p><br/><div style='display: inline-block;' id='" + id + "'></div>" +
		"<p id='qucode_status_" + orderNo +"'></p>" +
		"</div>", function () {
		location.href = location.href;
	});
	var qrcode = new QRCode(id, {
		text: content,
		width: 200,
		height: 200,
		colorDark : "#000000",
		colorLight : "#ffffff",
		correctLevel : QRCode.CorrectLevel.H
	});
	lunXun = true;
	queryOederStatus(orderNo);
}
var lunXun = true;
function queryOederStatus(orderNo){
	H.get($("#ctx").val() + "/orders/" + orderNo + "/" + spuFileId, function (res) {
		var html = "当前状态: 等待扫码";
		if (res.responseBody.status === 'WAIT_BUYER_PAY'){
			html = "当前状态: 等待支付(" + res.responseBody.payAccount + ")"
		}
		if (res.responseBody.status === 'TRADE_SUCCESS') {
			lunXun = false;
			html = "当前状态: 支付成功(" + res.responseBody.payAccount + "), 正在处理下载文件...";
			// 下载
			H.post(spuFileId+ "/download", {}, function (r) {
				if (r.success){
					$("#qucode_status_" + orderNo).html("已生成临时下载连接, 不要关闭本页面, 请在30分钟内重新点击下载按钮完成下载");
				}
			});
			// 十秒后自动刷新
			setTimeout(function () {
				location.href = location.href;
			}, 10000);
		}
		$("#qucode_status_" + orderNo).html(html);
	});
	if (lunXun){
		setTimeout(function () {
			queryOederStatus(orderNo);
		}, 500);
	}

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