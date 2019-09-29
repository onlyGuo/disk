host = $("#uploadUrl").val();
var signature = "";
var accessid = "";
var policyBase64 = "";
$.get($("#rootPath").val() + "/manager/sign", function(result){
	signature = result.responseBody.sign;
	accessid = result.responseBody.accessId;
	policyBase64 = result.responseBody.policyBase64;
	// var fileUuid = uuid();
	var fileUuid = $("#list_dir").val();
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : 'selectfiles', 
	    //runtimes : 'flash',
		container: document.getElementById('container'),
		flash_swf_url : $("#rootPath").val() + 'libs/upload/plupload-2.1.2/js/Moxie.swf',
		silverlight_xap_url : $("#rootPath").val() + 'libs/upload/plupload-2.1.2/js/Moxie.xap',

	    url : host,
		multipart_params: {
			'OSSAccessKeyId': accessid,
			'Filename': fileUuid + '${filename}',
	        'key' : fileUuid +'${filename}',
			'policy': policyBase64,
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
					console.log(file);
					document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
						+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
						+'</div>';
					file.name = result.responseBody;
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
	            if (info.status >= 200 || info.status < 200)
	            {
					parent.layer.alert("上传完毕", function () {
						parent.location = parent.location
					});

	            }else{
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



