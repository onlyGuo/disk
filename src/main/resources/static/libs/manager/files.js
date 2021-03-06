/**
 * Created by AnLuTong on 2016-12-21.
 */
var url = $("#list_api_dir").val();
$(function(){
	function listFiles(){
		var reqData = {
			make: $("#list_make").val(),
			keywork: $("#list_keyword").val(),
			dir: $("#list_dir").val()
		};
		H.post(url, reqData, function (res) {
			for(var i in res.list){
				appendFile(res.list[i])
			}
			if (res.make){
				$("#list_make").val(res.make);
				$(".fileList .more").text("加载更多");
			}else{
				$(".fileList .more").text("没有更多了");
			}
			init();
		});
	}

	listFiles();

	function init(){
		$(".workBtns").hide();
		$(".chickBox").click(function(){
			var isSelected = false;
			if($(this).attr("class") == "chickBox"){
				$(this).attr("class", "chickBox fa fa-check")
			}else{
				$(this).attr("class", "chickBox")
			}
			if($(this).attr("fileId") == 'all'){
				//全选
				$(".chickBox").attr("class", $(this).attr("class"));
			}

			var $box = $(".chickBox");
			for(var i = 0; i < $box.length; i++){
				if($($box[i]).attr("class") == "chickBox fa fa-check"){
					isSelected = true;
				}
			}

			if (isSelected){
				$(".workBtns").show();
			}else{
				$(".workBtns").hide();
			}
		});


		$(".fileList tbody tr").hover(function(){
			$(".fileWork").hide();
			$(this).find(".fileWork").show();
		}, function(){
			$(".fileWork").hide();
		});
	}

    
    
    //新建文件夹
    $("#createFolder").click(function(){
    	parent.layer.prompt({title: '新建文件夹名', formType: 0}, function(text, index){
			H.post("createFolder", {name:text}, function(response){
				parent.layer.close(index);
				appendFile({
					name: text,
					ossKey: $("#list_dir").val().substring(1) + text + "/",
					type: 0,
					size: "-"
				});
    		});
    	});
    });

	$(".btnGroup").click(function () {
		var $selects = $("tr .chickBox.fa.fa-check");
		var selectKeys = [];
		for (var i = 0; i < $selects.length; i++){
			selectKeys.push($($selects[i]).attr("fileId"));
		}
		parent.layer.msg('正在删除请稍候...', {icon: 16,time: 0,shade : [0.5 , '#000' , true]});
		H.post(url + "/../deletes", selectKeys, function(){
			for (var i = 0; i < $selects.length; i++){
				$($selects[i]).parent().parent().remove();
			}
			$(".workBtns").hide();
			parent.layer.msg("批量删除成功");
		});
	});
    
    //上传文件
    $("#uploadFile").click(function(){
		layer.open({
			type : 2,
			title : false,
			closeBtn : 1, // 不显示关闭按钮
			shade : [ 0 ],
			area : [ '500px', '400px' ],
			offset : 'rb', // 右下角弹出
			anim : 2,
			content : ['upload', 'yes' ], // iframe的url，no代表不显示滚动条
		});
    });

	$(".fileList .more").click(function () {
		if ($(this).text() === "加载更多"){
			listFiles();
		}
	});

});

/**
 * 获取文件
 * @param fileId
 * @returns
 */
function download(fileId){
	window.open($("#ctx").val() + '/files/download/' + fileId);
}

/**
 * 删除文件夹
 * @param fileId
 * @returns
 */
function deleteFolder(trId, fileId){
	var msg = "确定要删除该文件吗?";
	if (fileId.substring(fileId.length - 1) === "/"){
		msg = "删除文件夹后，其内的子文件和文件夹将会一起被删除，确定要删除吗?";
	}
	var index = parent.layer.confirm(msg, {
		  btn: ['继续','取消'] //按钮
		}, function(){
			//删除开始
			parent.layer.load();
			H.post(url + "/../delete", {name: fileId}, function () {
				parent.layer.closeAll('loading');
				parent.layer.msg("删除成功");
				$("#" + trId).remove();
			}, function (res) {
				parent.layer.closeAll('loading');
				parent.layer.msg(res.message);
			});
		}, function(){
			parent.layer.close(index);
		});
}

/**
 * 分享文件
 * @param fileId
 * @returns
 */
function shareFile(fileId){
	parent.layer.open({
		type : 2,
		title : "创建分享连接",
		area : [ '500px', '260px' ],
		anim : 2,
		content : [url + "/../share/" + fileId, 'yes' ], // iframe的url，no代表不显示滚动条
	});
}

/**
 * 查看分享连接
 * @param fileId
 * 		分享文件Id
 */
function lookShare(fileId){
	var link = window.location.protocol+"//"+window.location.host;
	link += $("#ctx").val() + "/share/" + fileId;
	parent.layer.alert("连接地址: " + link);
}

/**
 * 取消分享
 * @param fileId
 * @returns
 */
function deleteShare(fileId, t){
	parent.layer.load();
	H.post($("#ctx").val() + '/share/deleteShare/' + fileId, function(response){
		parent.layer.closeAll('loading');
		parent.layer.msg("已取消分享");
		$(t).parent().parent().remove();
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}

/**
 * 创建分享密码
 * @param fileId
 * @returns
 */
function createPassword(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/createSharePswd/' + fileId, function(response){
		parent.layer.closeAll('loading');
		parent.layer.alert(response,{
			area:['520px', '350px'],
			title:"创建分享链接"
		});
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("操作遇到错误");
	});
}

function deletePassword(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/deleteSharePassword/' + fileId, function(response){
		parent.layer.closeAll('loading');
		if(response.result){
			parent.layer.msg("已取消分享密码");
			location = location;
		}else{
			parent.layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}

/**
 * 像文件列表中添加内容
 * @param item
 */
function appendFile(item) {

	var href = "javascript:void(0);";
	var id = uuid();

	var fileName = item.name;
	var indexOf = fileName.indexOf(".");
	var fa = "fa-file-o";
	if (item.type === 0){
		fa = "fa-folder-o"
		href = url + "/" + item.ossKey;
	}else{
		if (indexOf !== 0){
			fileName = fileName.substring(indexOf);
			if (fileName){
				if (fileName.toUpperCase() === '.ZIP' || fileName.toUpperCase() === '.RAR'
					|| fileName.toUpperCase() === '.7Z' || fileName.toUpperCase() === '.TAR'
					|| fileName.toUpperCase() === '.ISO' || fileName.toUpperCase() === '.IMG'){
					fa = "fa-file-zip-o";
				}else if (fileName.toUpperCase() === '.JPG' || fileName.toUpperCase() === '.JPEG'
					|| fileName.toUpperCase() === '.GIF' || fileName.toUpperCase() === '.PNG'
					|| fileName.toUpperCase() === '.ICO' || fileName.toUpperCase() === '.BMP'){
					fa = "fa-file-image-o";
				}else if (fileName.toUpperCase() === '.TXT' || fileName.toUpperCase() === '.DOC'
					|| fileName.toUpperCase() === '.DOCX' || fileName.toUpperCase() === '.TIF'){
					fa = "fa-file-word-o";
				}else if (fileName.toUpperCase() === '.JS' || fileName.toUpperCase() === '.JAVA'
					|| fileName.toUpperCase() === '.CPP' || fileName.toUpperCase() === '.XML'
					|| fileName.toUpperCase() === '.HTML' || fileName.toUpperCase() === '.JSON'
					|| fileName.toUpperCase() === '.CSS' || fileName.toUpperCase() === '.DART'
					|| fileName.toUpperCase() === '.BAT' || fileName.toUpperCase() === '.SH'
					|| fileName.toUpperCase() === '.CMD' || fileName.toUpperCase() === '.H'){
					fa = "fa-file-code-o";
				}else if (fileName.toUpperCase() === '.MP3' || fileName.toUpperCase() === '.MP4'
					|| fileName.toUpperCase() === '.RMVB' || fileName.toUpperCase() === '.RM'
					|| fileName.toUpperCase() === '.AVI' || fileName.toUpperCase() === '.3GP'
					|| fileName.toUpperCase() === '.MPEG1-4' || fileName.toUpperCase() === '.MOV'
					|| fileName.toUpperCase() === '.MTV' || fileName.toUpperCase() === '.DAT'
					|| fileName.toUpperCase() === '.WMV' || fileName.toUpperCase() === '.AMV'
					|| fileName.toUpperCase() === '.FLV' || fileName.toUpperCase() === '.DMV'){
					fa = "fa-file-video-o";
				}
			}
		}
	}
	var updateTime = "-";
	if (item.updateTime){
		updateTime = item.updateTime;
	}

	$("tbody").append("<tr id=\"" + id + "\">\n" +
		"                <td>\n" +
		"                    <div class=\"chickBox\" fileId=\"" + item.ossKey + "\"></div>\n" +
		"                    <span class=\"fielName\">\n" +
		"                        <i class=\"fa " + fa + "\"></i>\n" +
		"                        <a href=\"" + href + "\">" + item.name + "</a>\n" +
		"                    </span>\n" +
		"                    <span class=\"fileWork\" hidden id=\"wk_" + id + "\">\n" +
		"                        <a href=\"javascript:download('" + item.ossKey + "');\"><i class=\"fa fa-cloud-download\"></i></a>\n" +
		"                        <a href=\"javascript:deleteFolder('" + id + "', '" + item.ossKey + "');\"><i class=\"fa fa-trash-o\"></i></a>\n" +
		"                        <a href=\"javascript:shareFile('" + item.ossKey + "');\"><i class=\"fa fa-share-alt-square\"></i></a>\n" +
		"                    </span>\n" +
		"                </td>\n" +
		"                <td>" + item.size + "</td>\n" +
		"                <td>" + updateTime + "</td>\n" +
		"            </tr>");
}


function uuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}