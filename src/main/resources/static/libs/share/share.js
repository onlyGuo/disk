

/**
 * 获取文件
 * @param fileId
 * @returns
 */
function download(fileId){
	H.post(fileId + "/download", {}, function (res) {
		window.open(res.responseBody.link);
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