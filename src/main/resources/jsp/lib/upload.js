var policyText = {
    "expiration": "2050-01-01T12:00:00.000Z", //设置该Policy的失效时间，超过这个失效时间之后，就没有办法通过这个policy上传文件了
    "conditions": [
    ["content-length-range", 0, 1048576000] // 设置上传文件的大小限制
    ]
};

accessid= 'sFXRMBFIpFIT9JIa';

host = 'http://aiyi-disk.oss-cn-beijing.aliyuncs.com';
var policyBase64 = Base64.encode(JSON.stringify(policyText))
var signature = "";
$.get($("#rootPath").val() + "/manager/getSign?encryptText="+policyBase64, function(result){
	signature = result;
	var fileUuid = uuid();
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : 'selectfiles', 
	    //runtimes : 'flash',
		container: document.getElementById('container'),
		flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
		silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',

	    url : host,
		multipart_params: {
			'Filename': fileUuid + '.${filename}', 
	        'key' : fileUuid +'.${filename}',
			'policy': policyBase64,
	        'OSSAccessKeyId': accessid, 
	        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
			'signature': signature,
		}, 

		init: {
			PostInit: function() {
				document.getElementById('ossfile').innerHTML = '';
				document.getElementById('postfiles').onclick = function() {
					uploader.start();
					return false;
				};
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					
//					console.log(file);
					$.post($("#rootPath").val() + "/manager/setTempFile",{name:fileUuid + "." + file.name, size:file.size, parentId:$("#parentId").val()}, function(result){
						if(result.result){
							document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
							+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
							+'</div>';
							file.name = result.responseBody;
						}else{
							parent.layer.msg(result.message);
							throw new Error("上传出错");
						}
						
					}).error(function(){
						parent.layer.msg("上传操作授权失败");
						throw new Error("上传出错");
					});
				});
			},

			UploadProgress: function(up, file) {
				var d = document.getElementById(file.id);
				d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
	            
	            var prog = d.getElementsByTagName('div')[0];
				var progBar = prog.getElementsByTagName('div')[0]
				progBar.style.width= 2*file.percent+'px';
				progBar.setAttribute('aria-valuenow', file.percent);
			},

			FileUploaded: function(up, file, info) {
	            //alert(info.status)
	            if (info.status >= 200 || info.status < 200)
	            {
	                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'success';
//	                console.log(file.name);
	                //临时文件转存为持久文件
	                $.post($("#rootPath").val() + "/manager/saveFile", {
	                	token:file.name,
	                }, function(response){
	                	if(response.result){
	                		parent.location = parent.location;
	                	}
	                });
	            }
	            else
	            {
	                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
	            } 
			},

			Error: function(up, err) {
				document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
			}
		}
	});
	
	uploader.init();
});
/*accesskey= 'ufu7nS8kS59awNihtjSonMETLI0KLy';
message = policyBase64
var bytes = Crypto.HMAC(Crypto.SHA1, message, accesskey, { asBytes: true }) ;
var signature = Crypto.util.bytesToBase64(bytes);*/
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
 
    var uuid = s.join("");
    return uuid + "/321aiyi_com";
}



